package com.halima.friendservice.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Table(name = "friends")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long friendId;
    @Column(name = "accepted_at")
    private LocalDateTime acceptedAt;


    @PrePersist
    protected void onCreate() {
        acceptedAt = LocalDateTime.now();
    }


}
