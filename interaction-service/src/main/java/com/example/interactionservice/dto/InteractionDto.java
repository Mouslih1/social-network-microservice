package com.example.interactionservice.dto;

import lombok.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InteractionDto {

    private List<CommentDto> comments;
    private List<ReactionDto> reactions;
    private List<SharedDto> shareds;
}
