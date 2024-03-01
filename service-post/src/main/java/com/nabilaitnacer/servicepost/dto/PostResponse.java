package com.nabilaitnacer.servicepost.dto;

import com.nabilaitnacer.servicepost.MediaDTO;
import com.nabilaitnacer.servicepost.PostEntityDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResponse {
    private PostEntityDto post;
    private List<MediaDTO> medias;
}
