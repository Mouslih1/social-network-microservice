package com.media.service.controller;

import com.media.service.dto.MediaDTO;
import com.media.service.service.impl.MediaServiceImpl;
import lombok.RequiredArgsConstructor;

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
                                              @RequestParam("postId") Long postId,@RequestParam("userId") Long userId) throws IOException {
        List<MediaDTO> mediaList = new ArrayList<>();
        for (MultipartFile file : files) {
            MediaDTO media = mediaService.addMedia(file, postId,userId);
            mediaList.add(media);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(mediaList);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteMedia(@RequestParam("userId") Long userId,
                                            @RequestParam("mediaId") Long mediaId,
                                            @RequestParam("postId") Long postId
                                            ) {
        mediaService.deleteMedia(userId, postId, mediaId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<MediaDTO>> getMediaByPostId(@PathVariable("postId") Long postId) {
        return ResponseEntity.ok(null);
    }

}