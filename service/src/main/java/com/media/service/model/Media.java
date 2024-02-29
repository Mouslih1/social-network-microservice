package com.media.service.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "media")
public class Media {
    @Id
    private String id;
    private String name;
    private String pathImage;
    private long postId;
    private String type;
    private long size;
    private LocalDateTime createdDate;
    private byte[] image;

}