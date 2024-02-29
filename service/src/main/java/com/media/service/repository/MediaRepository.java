package com.media.service.repository;

import com.media.service.model.Media;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MediaRepository extends MongoRepository<Media, String> {
    Optional<Media> findMediaByPostId(long postId);

    void deleteByPostId(long postId);
}
