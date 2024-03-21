package com.media.service.controller;


import com.media.service.dto.MediaDto;
import com.media.service.service.ImageService;
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
@CrossOrigin("*")
public class MediaController {


    private final ImageService mediaService;

    @PostMapping
    public ResponseEntity<List<MediaDto>> add(@RequestParam("files") List<MultipartFile> files,
                                              @RequestParam("postId") Long postId,@RequestParam("userId") Long userId) throws IOException {
        List<MediaDto> mediaList = new ArrayList<>();
        for (MultipartFile file : files) {
            MediaDto media = mediaService.upload(file, userId,postId);
            mediaList.add(media);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(mediaList);
    }

    @DeleteMapping("/{mediaUuid}")
    public ResponseEntity<Void> delete(@PathVariable String mediaUuid,@RequestParam("userId") Long userId,@RequestParam("postId") Long postId) {
        mediaService.delete(mediaUuid);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/post/{postId}")
    public List<MediaDto> getMediaByPostId(@PathVariable("postId") Long postId) {
        return mediaService.getMediaByPostId(postId);
    }

    @DeleteMapping("/post/{postId}")
    public void deleteMediaByPostId(@PathVariable("postId") Long postId) {
        mediaService.deleteMediaByPostId(postId);
    }




}