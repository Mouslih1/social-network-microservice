package com.example.interactionservice.controller;

import com.example.interactionservice.dto.SharedDto;
import com.example.interactionservice.exception.Error;
import com.example.interactionservice.service.IsharedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/shareds")
@CrossOrigin("*")
public class SharedController {

    private final IsharedService isharedService;

    @GetMapping
    public ResponseEntity<List<SharedDto>> all()
    {
        return new ResponseEntity<>(isharedService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/post/{postId}")
    public ResponseEntity<SharedDto> save(@PathVariable Long postId, @RequestBody SharedDto sharedDto)
    {
        return new ResponseEntity<>(isharedService.save(postId, sharedDto), HttpStatus.CREATED);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<SharedDto>> getSharedsByPost(@PathVariable Long postId)
    {
        return new ResponseEntity<>(isharedService.getAllSharedByPostId(postId), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Error> delete(@PathVariable Long id)
    {
        Error error = new Error("Shared deleted successfully");
        isharedService.delete(id);
        return new ResponseEntity<>(error, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SharedDto> update(@PathVariable Long id, @RequestBody SharedDto sharedDto)
    {
        return new ResponseEntity<>(isharedService.update(id, sharedDto), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SharedDto> byId(@PathVariable Long id)
    {
        return new ResponseEntity<>(isharedService.getById(id), HttpStatus.OK);
    }
}
