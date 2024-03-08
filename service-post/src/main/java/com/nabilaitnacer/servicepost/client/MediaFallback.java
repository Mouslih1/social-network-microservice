package com.nabilaitnacer.servicepost.client;

import com.nabilaitnacer.servicepost.dto.MediaDTO;
import com.nabilaitnacer.servicepost.exception.MediaException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public class MediaFallback implements MediaClient{
    @Override
    public ResponseEntity<List<MediaDTO>> add(List<MultipartFile> files, Long postId, Long userId) {
        throw new MediaException("Error lors de l ajout de medias.");
    }

    @Override
    public List<MediaDTO> getMediaByPostId(Long postId) {
        throw new MediaException("Erreur lors de la récupération des medias par ID de poste.");
    }

    @Override
    public ResponseEntity<Void> delete(String mediaUuid, Long userId, Long postId) {
        throw new MediaException("Erreur lors de la suppression du media.");
    }

    @Override
    public void deleteMediaByPostId(Long postId) {
        throw new MediaException("Erreur lors de la suppression de tous les medias lies à un poste.");
    }
}
