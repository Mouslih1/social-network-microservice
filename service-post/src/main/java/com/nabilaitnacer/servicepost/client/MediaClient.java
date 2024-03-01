package com.nabilaitnacer.servicepost.client;

import com.nabilaitnacer.servicepost.MediaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(url = "http://localhost:8016/api/v1/medias",name = "media-service")
public interface MediaClient {

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    List<MediaDTO> addMedia(@RequestPart("files") List<MultipartFile> files, @RequestPart("postId") Long postId);
    @DeleteMapping("/post/{postId}")
    void deleteMedia(@PathVariable("postId") Long postId);

    @GetMapping("/post/{postId}")
    List<MediaDTO> getMediaByPostId(@PathVariable("postId") Long postId);

}
