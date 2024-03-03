package com.nabilaitnacer.servicepost.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MediaDTO {
    Long id;
    String filename;
    Long userId;
    String uri;
    Long postId;
    String fileType;
    String mediaUuid;
    Long size;
    LocalDateTime createdDate;
}