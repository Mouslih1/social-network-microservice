package com.media.service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import  jakarta.persistence.Id;
import org.springframework.data.annotation.CreatedDate;


import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "medias")
@AllArgsConstructor
@NoArgsConstructor

public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String filename;
    private String mediaUuid;
    private Long userId;
    private String uri;
    private Long postId;
    private String fileType;
    private Long size;
    @CreatedDate
    private LocalDateTime createdDate;
}