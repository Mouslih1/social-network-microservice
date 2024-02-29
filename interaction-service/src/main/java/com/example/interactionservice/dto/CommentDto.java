package com.example.interactionservice.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CommentDto {

    private Long id;
    private Long postId;
    private Long userId;
    private int countComments;
    private String commentText;
    private LocalDateTime createdAt;
}
