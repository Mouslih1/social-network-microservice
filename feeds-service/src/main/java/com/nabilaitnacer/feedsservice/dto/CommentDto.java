package com.nabilaitnacer.feedsservice.dto;

import java.time.LocalDateTime;

public class CommentDto {
    private Long id;
    private Long postId;
    private Long userId;
    private String commentText;
    private LocalDateTime createdAt;
}
