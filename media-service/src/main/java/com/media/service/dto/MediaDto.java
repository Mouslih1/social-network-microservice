package com.media.service.dto;

import com.media.service.model.Media;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link Media}
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class MediaDto implements Serializable {
    Long id;
    String filename;
    Long userId;
    String uri;
    Long postId;
    String fileType;
    String filePath;
    Long size;
    LocalDateTime createdDate;
}