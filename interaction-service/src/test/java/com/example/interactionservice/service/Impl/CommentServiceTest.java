package com.example.interactionservice.service.Impl;

import com.example.interactionservice.dto.CommentDto;
import com.example.interactionservice.entity.Comment;
import com.example.interactionservice.repository.IcommentRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private IcommentRepository icommentRepository;

    @InjectMocks
    private CommentService commentService;

    @BeforeEach
    void setUp()
    {
        icommentRepository = Mockito.mock(IcommentRepository.class);
        ModelMapper modelMapper = new ModelMapper();
        commentService = new CommentService(icommentRepository, modelMapper);
    }

    @Test
    public void saveComment()
    {
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setPostId(1L);
        comment.setUserId(1L);
        comment.setCommentText("Commentaire 1");
        comment.setCreatedAt(LocalDateTime.now());

        CommentDto commentDto = new CommentDto();
        commentDto.setId(1L);
        commentDto.setPostId(1L);
        commentDto.setUserId(1L);
        commentDto.setCommentText("Commentaire 1");
        commentDto.setCreatedAt(LocalDateTime.now());

        when(icommentRepository.save(Mockito.any(Comment.class))).thenReturn(comment);

        CommentDto commentDtoSaved = commentService.save(commentDto.getPostId(),commentDto);
        Assertions.assertThat(commentDtoSaved.getId()).isEqualTo(comment.getId());
        Assertions.assertThat(commentDtoSaved).isNotNull();
    }

    @Test
    public void updateComment()
    {
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setPostId(1L);
        comment.setUserId(1L);
        comment.setCommentText("Commentaire 1");
        comment.setCreatedAt(LocalDateTime.now());

        CommentDto commentDto = new CommentDto();
        commentDto.setId(1L);
        commentDto.setPostId(1L);
        commentDto.setUserId(1L);
        commentDto.setCommentText("Commentaire 2");
        commentDto.setCreatedAt(LocalDateTime.now());

        when(icommentRepository.save(Mockito.any(Comment.class))).thenReturn(comment);
        when(icommentRepository.findById(1L)).thenReturn(Optional.of(comment));

        CommentDto commentDtoUpdated = commentService.update(1L, commentDto);
        Assertions.assertThat(commentDtoUpdated).isNotNull();
        Assertions.assertThat(commentDtoUpdated.getCommentText()).isEqualTo(comment.getCommentText());
    }

    @Test
    public void getByIdComment()
    {
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setPostId(1L);
        comment.setUserId(1L);
        comment.setCommentText("Commentaire 1");
        comment.setCreatedAt(LocalDateTime.now());

        CommentDto commentDto = new CommentDto();
        commentDto.setId(1L);
        commentDto.setPostId(1L);
        commentDto.setUserId(1L);
        commentDto.setCommentText("Commentaire 1");
        commentDto.setCreatedAt(LocalDateTime.now());

        when(icommentRepository.findById(1L)).thenReturn(Optional.of(comment));

        CommentDto commentGet = commentService.getById(1L);

        Assertions.assertThat(commentGet).isNotNull();
        Assertions.assertThat(commentGet.getId()).isEqualTo(comment.getId());
    }

    @Test
    public void getAllComments()
    {
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setPostId(1L);
        comment.setUserId(1L);
        comment.setCommentText("Commentaire 1");
        comment.setCreatedAt(LocalDateTime.now());

        CommentDto commentDto = new CommentDto();
        commentDto.setId(1L);
        commentDto.setPostId(1L);
        commentDto.setUserId(1L);
        commentDto.setCommentText("Commentaire 1");
        commentDto.setCreatedAt(LocalDateTime.now());

        when(icommentRepository.findAll()).thenReturn(Collections.singletonList(comment));

        List<CommentDto> comments = commentService.getAll();

        Assertions.assertThat(comments).isNotEmpty();
        Assertions.assertThat(comments).hasSize(1);
    }


    @Test
    public void getCommentsByPost()
    {
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setPostId(1L);
        comment.setUserId(1L);
        comment.setCommentText("Commentaire 1");
        comment.setCreatedAt(LocalDateTime.now());

        CommentDto commentDto = new CommentDto();
        commentDto.setId(1L);
        commentDto.setPostId(1L);
        commentDto.setUserId(1L);
        commentDto.setCommentText("Commentaire 1");
        commentDto.setCreatedAt(LocalDateTime.now());

        when(icommentRepository.findByPostId(1L)).thenReturn(Collections.singletonList(comment));

        List<CommentDto> comments = commentService.getAllCommentsByPostId(1L);

        Assertions.assertThat(comments).isNotEmpty();
        Assertions.assertThat(comments).hasSize(1);
    }
}