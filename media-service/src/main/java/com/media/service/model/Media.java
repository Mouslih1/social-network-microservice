package com.media.service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import  jakarta.persistence.Id;


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
    private String name;
    private String pathImage;
    private Long postId;
    private String type;
    private Long size;
    private LocalDateTime createdDate;
    private byte[] image;
}