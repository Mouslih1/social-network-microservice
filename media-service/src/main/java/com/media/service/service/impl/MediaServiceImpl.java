package com.media.service.service.impl;


import com.media.service.dto.MediaDTO;
import com.media.service.model.Media;
import com.media.service.repository.MediaRepository;
import com.media.service.service.MediaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class MediaServiceImpl  implements MediaService {

    private final MediaRepository mediaRepository;
    private  final  ModelMapper modelMapper;

    @Value("${file.storage.path}")
    private String fileStorageLocation;

    public MediaServiceImpl(MediaRepository mediaRepository, ModelMapper modelMapper) {
        this.mediaRepository = mediaRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public MediaDTO addMedia(MultipartFile file, long postId) throws IOException {
        String originalFileName = file.getOriginalFilename();
        String fileName = System.currentTimeMillis() + "_" + originalFileName;
        String url = fileStorageLocation + fileName;
        Path destinationPath = Paths.get(url);
        Files.copy(file.getInputStream(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
        MediaDTO mediaDTO = new MediaDTO();
        mediaDTO.setName(originalFileName);
        mediaDTO.setPathImage(url);
        mediaDTO.setPostId(postId);
        mediaDTO.setType(file.getContentType());
        mediaDTO.setSize(file.getSize());
        mediaDTO.setCreatedDate(LocalDateTime.now());
        Media media = modelMapper.map(mediaDTO, Media.class);
        mediaRepository.save(media);

        return modelMapper.map(media, MediaDTO.class);
    }



    @Override
    public MediaDTO getMediaByPostId(Long postId) {
        System.out.println(mediaRepository.findMediaByPostId(postId));
        Optional<Media> media = mediaRepository.findMediaByPostId(postId);
        MediaDTO mediaDTO = new MediaDTO();
        mediaDTO.setPostId(media.get().getPostId());
        mediaDTO.setPathImage(media.get().getPathImage());
        mediaDTO.setName(media.get().getName());
        mediaDTO.setType(media.get().getType());
        mediaDTO.setSize(media.get().getSize());
        mediaDTO.setCreatedDate(media.get().getCreatedDate());

        if (mediaDTO == null) {
            return null;
        }
        return mediaDTO;
    }

    @Transactional
    @Override
    public void deleteMedia(Long postId) {
        Optional<Media> mediaOptional = mediaRepository.findMediaByPostId(postId);
        if (mediaOptional.isPresent()) {
            mediaRepository.deleteByPostId(postId);
            String path = mediaOptional.get().getPathImage();
            Path pathfile = Paths.get(path);
            System.out.println(pathfile);
            try {
                Files.delete(pathfile);
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found");
            }


        } else {
            return;
        }
    }
}

