package com.example.interactionservice.repository;

import com.example.interactionservice.entity.Shared;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IsharedRepository extends JpaRepository<Shared, Long> {
    List<Shared> findByPostId(Long postId);
}
