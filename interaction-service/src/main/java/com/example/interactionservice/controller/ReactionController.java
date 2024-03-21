package com.example.interactionservice.controller;


import com.example.interactionservice.dto.ReactionDto;
import com.example.interactionservice.exception.Error;
import com.example.interactionservice.service.IreactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reactions")
@CrossOrigin("*")
public class ReactionController {

    private final IreactionService ireactionService;

    @PostMapping("/post/{postId}")
    public ResponseEntity<ReactionDto> save(@PathVariable Long postId, @RequestBody ReactionDto reactionDto)
    {
        return new ResponseEntity<>(ireactionService.save(postId, reactionDto), HttpStatus.CREATED);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<ReactionDto>> getByPostId(@PathVariable Long postId)
    {
        return new ResponseEntity<>(ireactionService.getAllReactionsByPostId(postId), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Error> delete(@PathVariable Long id)
    {
        Error error = new Error("Reaction deleted successfully");
        ireactionService.delete(id);
        return new ResponseEntity<>(error, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ReactionDto>> all()
    {
        return new ResponseEntity<>(ireactionService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReactionDto> byId(@PathVariable Long id)
    {
        return new ResponseEntity<>(ireactionService.getById(id), HttpStatus.OK);
    }
}
