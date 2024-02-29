package com.media.service.service;

import com.media.service.dto.MediaDTO;
import org.springframework.web.multipart.MultipartFile;

public interface MediaService {

    MediaDTO getMediaByPostId(Long postId);

    void deleteMedia(Long id) throws Exception;
    MediaDTO addMedia(MultipartFile file, long postId) throws Exception;


}
