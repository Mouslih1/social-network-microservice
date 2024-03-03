package com.nabilaitnacer.servicepost.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {

    private String body;
    private List<MultipartFile> multipartFiles;
}