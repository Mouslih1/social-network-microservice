package com.example.interactionservice.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private Long id;
    private Long postId;
    private Long userId;
    private String commentText;
    private LocalDateTime createdAt;
}
