package com.media.service.dto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MediaDTO {
    private String id;
    private String name;
    private String pathImage;
    private String type;
    private long size;
    private long postId;
    private LocalDateTime createdDate;

}