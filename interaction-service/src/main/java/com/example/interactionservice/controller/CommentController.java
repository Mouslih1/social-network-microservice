package com.example.interactionservice.controller;

import com.example.interactionservice.dto.CommentDto;
import com.example.interactionservice.service.IcommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.interactionservice.exception.Error;

import java.util.Enumeration;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
public class CommentController {

    private final IcommentService icommentService;

    @PostMapping("/post/{postId}")
    public ResponseEntity<CommentDto> save(@PathVariable Long postId, @RequestBody CommentDto comment,@RequestHeader("id") String id)
    {
        System.out.println("id =========== 1 " + id);
        return new ResponseEntity<>(icommentService.save(postId, comment), HttpStatus.CREATED);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentDto>> getByPostId(@PathVariable Long postId)
    {
        return new ResponseEntity<>(icommentService.getAllCommentsByPostId(postId), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Error> delete(@PathVariable Long id)
    {
        Error error = new Error("Comment deleted successfully");
        icommentService.delete(id);
        return new ResponseEntity<>(error, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CommentDto>> all()
    {
        return new ResponseEntity<>(icommentService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> byId(@PathVariable Long id)
    {
        return new ResponseEntity<>(icommentService.getById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentDto> update(@PathVariable Long id, @RequestBody CommentDto commentDto)
    {
        return new ResponseEntity<>(icommentService.update(id, commentDto), HttpStatus.OK);
    }
}
