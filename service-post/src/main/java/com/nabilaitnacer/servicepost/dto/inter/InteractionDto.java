package com.nabilaitnacer.servicepost.dto.inter;

import lombok.*;

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
    private int countComments;
    private int countShareds;
    private int countReactions;
    private int countLikes;
    private int countLoves;
    private int countSad;
    private int countAngry;
    private int countHahah;
    private int countWow;

}
