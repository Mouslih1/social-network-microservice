package com.nabilaitnacer.servicepost;



import com.nabilaitnacer.servicepost.dto.PostRequest;
import com.nabilaitnacer.servicepost.dto.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/post")
@Slf4j
public class PostController {

    private final  PostService postService;


    @PostMapping
    public ResponseEntity<PostResponse> createPost(@RequestHeader("id") String userId ,@ModelAttribute PostRequest postRequest) {
        return ResponseEntity.ok(postService.createPost(getUserId(userId),postRequest));
    }



    private Long getUserId(String userId) {
        return Long.parseLong(userId);
    }




}
