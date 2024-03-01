package com.nabilaitnacer.servicepost;

import com.nabilaitnacer.servicepost.client.MediaClient;
import com.nabilaitnacer.servicepost.dto.PostRequest;
import com.nabilaitnacer.servicepost.dto.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
@Slf4j
public class PostController {
    private final  PostService postService;
    private final MediaClient mediaClient;


    @GetMapping("/{id}")
    public ResponseEntity<PostEntityDto> getPostById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostResponse>> getPostsByUserId(@PathVariable Long userId) {
        List<PostEntityDto> postEntityDtos = postService.getPostsByUserId(userId);
        List<PostResponse> postResponses = postEntityDtos.stream().map(postEntityDto -> {
            log.info("postEntityDto: {}", postEntityDto);
            log.info("id: {}", postEntityDto.getId());
            List<MediaDTO> mediaDTOS = mediaClient.getMediaByPostId(postEntityDto.getId());
            return PostResponse.builder().post(postEntityDto).medias(mediaDTOS).build();
        }).toList();
        log.info("postResponses: {}", postResponses);
        return ResponseEntity.ok(postResponses);

    }


    @PostMapping
    public ResponseEntity<PostResponse> createPost(@ModelAttribute PostRequest postRequest) {
        log.info("postRequest: {}", postRequest);
        PostEntityDto postEntityDto = PostEntityDto.builder()
                .body(postRequest.getBody())
                .userId(Long.parseLong(postRequest.getUserId()))
                .build();
        List<MultipartFile> mediaList = postRequest.getMultipartFiles();

        PostEntityDto postEntityDtoSaved = postService.createPost(postEntityDto);

        List<MediaDTO> mediaDTOS =  mediaClient.addMedia(mediaList, postEntityDtoSaved.getId());
        log.info("mediaDTOS: {}", mediaDTOS);
        return ResponseEntity.ok(PostResponse.builder().post(postEntityDtoSaved).medias(mediaDTOS).build());
    }
    @PutMapping
    public ResponseEntity<PostEntityDto> updatePost(Long id, PostEntityDto postEntityDto) {
        return ResponseEntity.ok(postService.updatePost(id, postEntityDto));
    }
    @DeleteMapping
    public ResponseEntity<Void> deletePost(Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok().build();
    }




}
