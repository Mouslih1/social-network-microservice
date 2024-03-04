package com.example.interactionservice.service;

import com.example.interactionservice.dto.ReactionDto;
import com.example.interactionservice.dto.SharedDto;

import java.util.List;

public interface IsharedService {

    SharedDto save(Long postId, SharedDto sharedDto);
    SharedDto update(Long id, SharedDto sharedDto);
    SharedDto getById(Long id);
    List<SharedDto> getAllSharedByPostId(Long postId);
    int getCountSharedsOfPost(Long postId);
    List<SharedDto> getAll();
    void delete(Long id);
}
