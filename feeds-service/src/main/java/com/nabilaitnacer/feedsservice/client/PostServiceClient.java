package com.nabilaitnacer.feedsservice.client;

import com.nabilaitnacer.feedsservice.dto.PostDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "post-service", fallback = PostClientFallback.class)
public interface PostServiceClient {

    @GetMapping("/post-service/post/{postId}")
    PostDto getPost(@PathVariable("postId") Long postId);

    @GetMapping("/post-service/post/user/{userId}")
    List<PostDto> getPostByUser(@PathVariable("userId") Long userId);
    //TODO send list of user ids and get list of posts



}
