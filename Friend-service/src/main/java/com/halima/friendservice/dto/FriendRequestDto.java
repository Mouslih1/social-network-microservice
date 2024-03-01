package com.halima.friendservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FriendRequestDto {
    private Long id;
    private Long userIdSender;
    private Long friendId;
    private String status;
    private String createdAt;
}
