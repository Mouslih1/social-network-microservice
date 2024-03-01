package com.media.service.repository;

import com.media.service.model.Media;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MediaRepository extends JpaRepository<Media, Long> {
    List<Media> findMediaByPostId(Long postId);

    void deleteByPostId(Long postId);
    Optional<Media> findByIdAndPostId(Long id, Long postId);
}
