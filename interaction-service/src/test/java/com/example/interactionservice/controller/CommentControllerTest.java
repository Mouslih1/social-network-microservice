package com.example.interactionservice.controller;

import com.example.interactionservice.dto.CommentDto;
import com.example.interactionservice.entity.Comment;
import com.example.interactionservice.service.Impl.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@WebMvcTest(controllers = CommentController.class)
@ExtendWith(MockitoExtension.class)
class CommentControllerTest {

    @MockBean
    private CommentService commentService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    CommentDto commentDto;

    Comment comment;

    @BeforeEach
    void init()
    {
        comment = new Comment();
        comment.setId(1L);
        comment.setPostId(1L);
        comment.setUserId(1L);
        comment.setCommentText("Commentaire 1");
        comment.setCreatedAt(LocalDateTime.now());

        commentDto = new CommentDto();
        commentDto.setId(1L);
        commentDto.setPostId(1L);
        commentDto.setUserId(1L);
        commentDto.setCommentText("Commentaire 1");
        commentDto.setCreatedAt(LocalDateTime.now());
    }

    @Test
    public void saveComment() throws Exception {
        when(commentService.save(1L, commentDto)).thenReturn(commentDto);

        ResultActions response = mockMvc.perform(post("/api/v1/comments/post/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(comment)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateComment() throws Exception {
        when(commentService.update(1L, commentDto)).thenReturn(commentDto);

        ResultActions response = mockMvc.perform(put("/api/v1/comments/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(comment)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getByIdComment() throws Exception {
        when(commentService.getById(1L)).thenReturn(commentDto);

        ResultActions response = mockMvc.perform(get("/api/v1/comments/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getAllComments() throws Exception {
        when(commentService.getAll()).thenReturn(Collections.singletonList(commentDto));

        ResultActions response = mockMvc.perform(get("/api/v1/comments")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getCommentsByPost() throws Exception {
        when(commentService.getAllCommentsByPostId(1L)).thenReturn(Collections.singletonList(commentDto));

        ResultActions response = mockMvc.perform(get("/api/v1/comments/post/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void deleteComment() throws Exception {
        doNothing().when(commentService).delete(1L);

        ResultActions response = mockMvc.perform(delete("/api/v1/comments/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

}