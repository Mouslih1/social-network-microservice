package com.example.interactionservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Entity
@Table(name = "shareds")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE shareds SET is_delete = true WHERE id=?")
@Where(clause = "is_delete = false")
public class Shared {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long postId;
    private Long userId;
    private boolean isDelete = Boolean.FALSE;
    private LocalDateTime sharedAt;
}
