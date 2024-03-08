package com.nabilaitnacer.servicepost.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostEntityDto implements Serializable {

    Long id;
    String body;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    boolean isDeleted;
    Long userId;
}