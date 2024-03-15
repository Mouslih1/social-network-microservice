package com.nabilaitnacer.servicepost;

import com.nabilaitnacer.servicepost.client.InteractionClient;
import com.nabilaitnacer.servicepost.client.MediaClient;
import com.nabilaitnacer.servicepost.dto.*;
import com.nabilaitnacer.servicepost.dto.inter.InteractionDto;
import com.nabilaitnacer.servicepost.exception.PostException;
import com.nabilaitnacer.servicepost.exception.PostNotFoundException;
import com.nabilaitnacer.servicepost.producer.PostProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    @Qualifier("com.nabilaitnacer.servicepost.client.MediaClient")
    private final MediaClient mediaClient;
    @Qualifier("com.nabilaitnacer.servicepost.client.InteractionClient")
    private final InteractionClient interactionClient;
    private final PostProducer producer;

    @Transactional
    public PostResponse createPost(Long userId, PostRequest postRequest) {
        PostEntityDto postEntityDto = PostEntityDto.builder()
                .body(postRequest.getBody())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .userId(userId)
                .build();
        PostEntity postEntity = modelMapper.map(postEntityDto, PostEntity.class);
        postEntity = postRepository.save(postEntity);
        PostResponse postResponse = new PostResponse();
        postResponse.setPost(postEntityDto);
        postResponse.getPost().setId(postEntity.getId());
        if (postRequest.getMultipartFiles() != null && !postRequest.getMultipartFiles().isEmpty()) {
            postResponse.setMedias(mediaClient.add(postRequest.getMultipartFiles(), postEntity.getId(), userId).getBody());

        }
        producer.sendMessage(new PostProducerDto(postResponse.getPost().getId(), postResponse.getPost().getBody(), postResponse.getPost().getUserId(), null));
        return postResponse;
    }


    public PostResponse updatePost(Long userId, Long id, PostUpdateRequest postUpdateRequest) {

        //check if the post exist
        PostEntity postEntity = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("Post not found"));
        //check if the user is the owner of the post
        if (!postEntity.getUserId().equals(userId)) throw new PostException("You are not the owner of the post");
        //check if there is media to delete
        if (postUpdateRequest.getMediaUuidsToDelete() != null && !postUpdateRequest.getMediaUuidsToDelete().isEmpty()) {
            postUpdateRequest.getMediaUuidsToDelete().forEach(mediaUuid -> {
                // call media client to delete media
                //send post id and media id and user id
                mediaClient.delete(mediaUuid,  userId, id);
            });
        }
        // get the list of media for this post
        List<MediaDTO> mediaDTOS = mediaClient.getMediaByPostId(id);
        log.info("mediaDTOS {} ", mediaDTOS);
        //check if there is media to add
        if (postUpdateRequest.getMultipartFiles() != null && !postUpdateRequest.getMultipartFiles().isEmpty()) {

            List<MediaDTO> mediaDTOS1 = mediaClient.add(postUpdateRequest.getMultipartFiles(), id, userId).getBody();
            mediaDTOS.addAll(mediaDTOS1);
        }

        postEntity.setUpdatedAt(LocalDateTime.now());
        postEntity.setBody(postUpdateRequest.getBody());
        postEntity = postRepository.save(postEntity);
        PostResponse postResponse = new PostResponse();
        postResponse.setPost(modelMapper.map(postEntity, PostEntityDto.class));
        postResponse.setMedias(mediaDTOS);
        return postResponse;
    }

  public void deletePost(Long userId, Long id) {
        PostEntity postEntity = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("Post not found"));
        if (!postEntity.getUserId().equals(userId)) throw new PostException("You are not the owner of the post");
        mediaClient.deleteMediaByPostId(id);
        postRepository.delete(postEntity);

    }

    public List<PostEntityDto> getPostsByUserId(Long id) {
        log.info("id: {}", id);
        return postRepository.findPostEntitiesByUserId(id).stream()
                .map(postEntity -> modelMapper.map(postEntity, PostEntityDto.class))
                .toList();
    }

    public List<PostWithInteractionResponse> getAllPost() {
        List<PostWithInteractionResponse> postWithInteractionResponses = new ArrayList<>();
        List<PostEntity> postEntities = postRepository.findAll();
        postEntities.forEach(postEntity -> {
            List<MediaDTO> mediaDTOS = mediaClient.getMediaByPostId(postEntity.getId());
            PostWithInteractionResponse postWithInteractionResponse = new PostWithInteractionResponse();
            postWithInteractionResponse.setPostResponse(PostResponse.builder()
                    .post(modelMapper.map(postEntity, PostEntityDto.class))
                    .medias(mediaDTOS)
                    .build());
            InteractionDto intercationResponse = interactionClient.getInteractionsOfPost(postEntity.getId()).getBody();
            postWithInteractionResponse.setInteractionDto(intercationResponse);
            postWithInteractionResponses.add(postWithInteractionResponse);

        });
        return postWithInteractionResponses;
    }
}
