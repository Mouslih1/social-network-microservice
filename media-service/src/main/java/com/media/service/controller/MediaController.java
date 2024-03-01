// MediaController.java
package com.media.service.controller;

import com.media.service.dto.MediaDTO;
import com.media.service.service.impl.MediaServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/medias")
@RequiredArgsConstructor
public class MediaController {


    private final MediaServiceImpl mediaService;

    @PostMapping
    public ResponseEntity<List<MediaDTO>> add(@RequestParam("files") List<MultipartFile> files,
                                              @RequestParam("postId") Long postId) throws IOException {
        List<MediaDTO> mediaList = new ArrayList<>();
        for (MultipartFile file : files) {
            MediaDTO media = mediaService.addMedia(file, postId);
            mediaList.add(media);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(mediaList);
    }

    @DeleteMapping("/post/{postId}")
    public ResponseEntity<Void> deleteMedia(@PathVariable("postId") Long postId) throws IOException {
        //TODO implement this method
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<MediaDTO>> getMediaByPostId(@PathVariable("postId") Long postId) {
        return ResponseEntity.ok(null);
    }

}