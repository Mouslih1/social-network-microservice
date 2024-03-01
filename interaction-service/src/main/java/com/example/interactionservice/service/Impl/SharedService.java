package com.example.interactionservice.service.Impl;

import com.example.interactionservice.dto.SharedDto;
import com.example.interactionservice.entity.Shared;
import com.example.interactionservice.exception.NotFoundException;
import com.example.interactionservice.repository.IsharedRepository;
import com.example.interactionservice.service.IsharedService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SharedService implements IsharedService {

    private final IsharedRepository isharedRepository;
    private final ModelMapper modelMapper;
    private final String SHARED_NOT_FOUND = "Shared not found with this id : ";

    @Override
    public SharedDto save(Long postId, SharedDto sharedDto)
    {
        sharedDto.setSharedAt(LocalDateTime.now());
        sharedDto.setPostId(postId);
        Shared sharedSaved = isharedRepository.save(modelMapper.map(sharedDto, Shared.class));
        return modelMapper.map(sharedSaved, SharedDto.class);
    }

    @Override
    public SharedDto update(Long id, SharedDto sharedDto)
    {
        Shared shared = isharedRepository.findById(id).orElseThrow(() -> new NotFoundException(SHARED_NOT_FOUND + id));
        shared.setPostId(sharedDto.getPostId());
        shared.setUserId(sharedDto.getUserId());
        Shared sharedUpdated = isharedRepository.save(shared);
        return modelMapper.map(sharedUpdated, SharedDto.class);
    }

    @Override
    public SharedDto getById(Long id)
    {
        Shared shared = isharedRepository.findById(id).orElseThrow(() -> new NotFoundException(SHARED_NOT_FOUND + id));
        return modelMapper.map(shared, SharedDto.class);
    }

    @Override
    public List<SharedDto> getAllSharedByPostId(Long postId)
    {
        List<Shared> shareds = isharedRepository.findByPostId(postId);
        return shareds
                .stream()
                .map(shared ->modelMapper.map(shared, SharedDto.class))
                .toList();
    }

    @Override
    public int getCountSharedsOfPost(Long postId)
    {
        List<Shared> shareds = isharedRepository.findByPostId(postId);
        return shareds.size();
    }

    @Override
    public List<SharedDto> getAll()
    {
        List<Shared> shareds = isharedRepository.findAll();
        return shareds
                .stream()
                .map(shared -> modelMapper.map(shared, SharedDto.class))
                .toList();
    }

    @Override
    public void delete(Long id)
    {
        isharedRepository.deleteById(id);
    }
}
