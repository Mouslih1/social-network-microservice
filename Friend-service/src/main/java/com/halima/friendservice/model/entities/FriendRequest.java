package com.halima.friendservice.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Setter
@Getter
@Table(name = "friend_request")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FriendRequest {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private Long userIdSender;
        private Long friendId;
        private String status = "PENDING";
        private Date createdAt;

}
