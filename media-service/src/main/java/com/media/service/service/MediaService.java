package com.media.service.service;

import com.media.service.dto.MediaDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MediaService {

    List<MediaDTO> getMediaByPostId(Long postId);

    void deleteMedia(Long userId, Long postId, Long mediaId) throws Exception;
    MediaDTO addMedia(MultipartFile file, long postId,Long userId) throws Exception;


}
