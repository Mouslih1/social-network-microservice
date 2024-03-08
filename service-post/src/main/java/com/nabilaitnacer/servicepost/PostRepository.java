package com.nabilaitnacer.servicepost;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<PostEntity, Long> {

    List<PostEntity> findPostEntitiesByUserId(Long id);

}
