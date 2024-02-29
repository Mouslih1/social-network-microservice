// MediaController.java
package com.media.service.controller;

import com.media.service.dto.MediaDTO;
import com.media.service.service.impl.MediaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/media")
public class MediaController {

    @Autowired
    private MediaServiceImpl mediaService;



    @PostMapping("/add")
    public ResponseEntity<MediaDTO> add(@RequestParam("file") MultipartFile file,
                                        @RequestParam("postId") long postId) throws IOException {
        MediaDTO media = mediaService.addMedia(file, postId);
        return  ResponseEntity.status(HttpStatus.CREATED).body(media);
    }


    @DeleteMapping("/post/{postId}")
    public ResponseEntity<Void> deleteMedia(@PathVariable("postId") Long postId) throws IOException {
        mediaService.deleteMedia(postId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<MediaDTO> getMediaByPostId(@PathVariable("postId") long postId) {
        MediaDTO media = mediaService.getMediaByPostId(postId);
        return ResponseEntity.ok(media);
    }

}