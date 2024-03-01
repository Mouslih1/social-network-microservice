package com.halima.friendservice.dto;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FriendDto {
    private Long id;
    private Long userIdSender;
    private Long friendId;

}
