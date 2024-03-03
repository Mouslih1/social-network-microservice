package com.nabilaitnacer.servicepost;

import com.nabilaitnacer.servicepost.client.MediaClient;
import com.nabilaitnacer.servicepost.dto.*;
import com.nabilaitnacer.servicepost.exception.PostException;
import com.nabilaitnacer.servicepost.exception.PostNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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
    private final MediaClient mediaClient;

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


}
