package com.example.interactionservice.service.Impl;

import com.example.interactionservice.dto.CommentDto;
import com.example.interactionservice.dto.InteractionDto;
import com.example.interactionservice.dto.ReactionDto;
import com.example.interactionservice.dto.SharedDto;
import com.example.interactionservice.service.IcommentService;
import com.example.interactionservice.service.IinteractionService;
import com.example.interactionservice.service.IreactionService;
import com.example.interactionservice.service.IsharedService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class InteractionService implements IinteractionService {

    private final IcommentService icommentService;
    private final IsharedService isharedService;
    private final IreactionService ireactionService;

    @Override
    public InteractionDto getInteractionsOfPost(Long postId)
    {
        InteractionDto interactionDto = new InteractionDto();
        List<CommentDto> comments = icommentService.getAllCommentsByPostId(postId);
        List<SharedDto> shareds = isharedService.getAllSharedByPostId(postId);
        List<ReactionDto> reactions = ireactionService.getAllReactionsByPostId(postId);
        interactionDto.setComments(comments);
        interactionDto.setShareds(shareds);
        interactionDto.setReactions(reactions);
        return interactionDto;
    }
}
