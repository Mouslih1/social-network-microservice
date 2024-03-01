package com.example.interactionservice.service.Impl;

import com.example.interactionservice.dto.ReactionDto;
import com.example.interactionservice.entity.Reaction;
import com.example.interactionservice.entity.enums.ReactionType;
import com.example.interactionservice.exception.NotFoundException;
import com.example.interactionservice.repository.IreactionRepository;
import com.example.interactionservice.service.IreactionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReactionService implements IreactionService {

    private final IreactionRepository ireactionRepository;
    private final ModelMapper modelMapper;
    private static final String REACTION_NOT_FOUND = "Reaction not found with this id : ";

    @Override
    public ReactionDto save(Long postId, ReactionDto reactionDto)
    {
        reactionDto.setCreatedAt(LocalDateTime.now());
        reactionDto.setPostId(postId);
        Reaction reaction = ireactionRepository.save(modelMapper.map(reactionDto, Reaction.class));
        return modelMapper.map(reaction, ReactionDto.class);
    }

    @Override
    public ReactionDto update(Long id, ReactionDto reactionDto)
    {
        Reaction reaction = ireactionRepository.findById(id).orElseThrow(() -> new NotFoundException(REACTION_NOT_FOUND + id));
        reaction.setReactionType(reactionDto.getReactionType());
        Reaction reactionSaved = ireactionRepository.save(reaction);
        return modelMapper.map(reactionSaved, ReactionDto.class);
    }

    @Override
    public ReactionDto getById(Long id)
    {
        Reaction reaction = ireactionRepository.findById(id).orElseThrow(() -> new NotFoundException(REACTION_NOT_FOUND + id));
        return modelMapper.map(reaction, ReactionDto.class);
    }

    @Override
    public List<ReactionDto> getAllReactionsByPostId(Long postId)
    {
        List<Reaction> reactions = ireactionRepository.findByPostId(postId);
        return reactions
                .stream()
                .map(reaction ->modelMapper.map(reaction, ReactionDto.class))
                .toList();
    }

    @Override
    public List<ReactionDto> getAll()
    {
        List<Reaction> reactions = ireactionRepository.findAll();
        return reactions
                .stream()
                .map(reaction -> modelMapper.map(reaction, ReactionDto.class))
                .toList();
    }

    @Override
    public int getLikeCountOfPost(Long postId)
    {
        List<Reaction> reactions = ireactionRepository.findByPostId(postId);
        return (int) reactions.stream()
                .filter(reaction -> reaction.getReactionType() == ReactionType.LIKE)
                .count();
    }

    @Override
    public int getLoveCountOfPost(Long postId)
    {
        List<Reaction> reactions = ireactionRepository.findByPostId(postId);
        return (int) reactions.stream()
                .filter(reaction -> reaction.getReactionType() == ReactionType.LOVE)
                .count();
    }

    @Override
    public int getWowCountOfPost(Long postId)
    {
        List<Reaction> reactions = ireactionRepository.findByPostId(postId);
        return (int) reactions.stream()
                .filter(reaction -> reaction.getReactionType() == ReactionType.WOW)
                .count();
    }

    @Override
    public int getHahahCountOfPost(Long postId)
    {
        List<Reaction> reactions = ireactionRepository.findByPostId(postId);
        return (int) reactions.stream()
                .filter(reaction -> reaction.getReactionType() == ReactionType.HAHAH)
                .count();
    }

    @Override
    public int getSadCountOfPost(Long postId)
    {

        List<Reaction> reactions = ireactionRepository.findByPostId(postId);
        return (int) reactions.stream()
                .filter(reaction -> reaction.getReactionType() == ReactionType.SAD)
                .count();
    }

    @Override
    public int getAngryCountOfPost(Long postId)
    {
        List<Reaction> reactions = ireactionRepository.findByPostId(postId);
        return (int) reactions.stream()
                .filter(reaction -> reaction.getReactionType() == ReactionType.ANGRY)
                .count();
    }

    @Override
    public int getCountReactionsOfPost(Long postId)
    {
        List<Reaction> reactions = ireactionRepository.findByPostId(postId);
        return reactions.size();
    }

    @Override
    public void delete(Long id)
    {
        ireactionRepository.deleteById(id);
    }
}
