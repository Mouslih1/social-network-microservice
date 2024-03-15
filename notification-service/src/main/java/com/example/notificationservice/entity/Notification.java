package com.example.notificationservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "notifications")
@SQLDelete(sql = "UPDATE notifications SET is_delete = true WHERE id=?")
@Where(clause = "is_delete = false")
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long relatedId;
    private String message;
    @CreatedDate
    private LocalDateTime createdAt;
    private boolean seen;
    private Long userReceiver;
    private boolean isDelete = Boolean.FALSE;
}
