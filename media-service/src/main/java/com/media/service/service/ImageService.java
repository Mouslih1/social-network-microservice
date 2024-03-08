package com.media.service.service;

import com.media.service.dto.MediaDto;
import com.media.service.exception.MediaException;
import com.media.service.model.Media;
import com.media.service.repository.MediaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImageService {

    private final FileStorageService fileStorageService;

    private final MediaRepository mediaRepository;
    private final ModelMapper modelMapper;


    public MediaDto upload(MultipartFile file, Long userId,Long postId) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());

        log.info("storing file {}", filename);

        MediaDto metadata = fileStorageService.store(file, userId, postId);
        log.info("metadata: {}", metadata);
         Media media = mediaRepository.save(modelMapper.map(metadata, Media.class));
        metadata.setId(media.getId());
        metadata.setCreatedDate(media.getCreatedDate());

        return metadata;
    }
    public void delete(String mediaUuid) {
        Media media = mediaRepository.findByMediaUuid(mediaUuid)
                .orElseThrow(() -> new MediaException("Media not found with uuid: " + mediaUuid));
        fileStorageService.delete(media.getFilename());
        mediaRepository.delete(media);
    }

    public List<MediaDto> getMediaByPostId(Long postId) {
        List<Media> mediaList = mediaRepository.findByPostId(postId);
        return modelMapper.map(mediaList, List.class);
    }

    public void deleteMediaByPostId(Long postId) {
        mediaRepository.findByPostId(postId).forEach(media -> {
            fileStorageService.delete(media.getFilename());
            mediaRepository.delete(media);
        });
    }
}
