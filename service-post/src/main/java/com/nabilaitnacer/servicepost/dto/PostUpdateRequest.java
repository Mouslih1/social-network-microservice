package com.nabilaitnacer.servicepost.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * This class is used to update a post entity in the database
 */
public class PostUpdateRequest {
    // new body to update the post entity in the database
    private String body;
    // new media to add to the post entity in the database
    private List<MultipartFile> multipartFiles;
    // ids of media to delete from the post entity in the database
    private List<String> mediaUuidsToDelete;
}
