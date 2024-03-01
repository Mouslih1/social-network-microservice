package com.nabilaitnacer.servicepost;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MediaDTO {
    private Long id;
    private String name;
    private String pathImage;
    private Long postId;
    private String type;
    private Long size;
    private LocalDateTime createdDate;
}