package com.example.interactionservice.service.Impl;

import com.example.interactionservice.dto.CommentDto;
import com.example.interactionservice.entity.Comment;
import com.example.interactionservice.exception.NotFoundException;
import com.example.interactionservice.repository.IcommentRepository;
import com.example.interactionservice.service.IcommentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService implements IcommentService {

    private final IcommentRepository icommentRepository;
    private final ModelMapper modelMapper;
    private static final String COMMENT_NOT_FOUND = "Comment not found with this id : ";

    @Override
    public CommentDto save(Long postId, CommentDto commentDto)
    {
        commentDto.setCreatedAt(LocalDateTime.now());
        commentDto.setPostId(postId);
        Comment comment = icommentRepository.save(modelMapper.map(commentDto, Comment.class));
        return modelMapper.map(comment, CommentDto.class);
    }

    @Override
    public CommentDto update(Long id, CommentDto commentDto)
    {
        Comment comment = icommentRepository.findById(id).orElseThrow(() -> new NotFoundException(COMMENT_NOT_FOUND + id));
        comment.setCommentText(commentDto.getCommentText());
        Comment commentUpdated = icommentRepository.save(comment);
        return modelMapper.map(commentUpdated, CommentDto.class);
    }

    @Override
    public CommentDto getById(Long id)
    {
        Comment comment = icommentRepository.findById(id).orElseThrow(() -> new NotFoundException(COMMENT_NOT_FOUND + id));
        return modelMapper.map(comment, CommentDto.class);
    }

    @Override
    public List<CommentDto> getAllCommentsByPostId(Long postId)
    {
        List<Comment> comments = icommentRepository.findByPostId(postId);
        return comments
                .stream()
                .map(comment -> modelMapper.map(comment,CommentDto.class))
                .toList();
    }

    @Override
    public List<CommentDto> getAll()
    {
        List<Comment> comments = icommentRepository.findAll();
        return comments
                .stream()
                .map(comment -> modelMapper.map(comment, CommentDto.class))
                .toList();
    }

    @Override
    public int getCountOfCommentsByPost(Long postId)
    {
        List<Comment> comments = icommentRepository.findByPostId(postId);
        return comments.size();
    }

    @Override
    public void delete(Long id)
    {
        icommentRepository.deleteById(id);
    }
}
