package com.example.interactionservice.entity;


import com.example.interactionservice.entity.enums.ReactionType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Entity
@Table(name = "reactions")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE reactions SET is_delete = true WHERE id=?")
@Where(clause = "is_delete = false")
public class Reaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long postId;
    private Long userId;
    private ReactionType reactionType;
    private boolean isDelete = Boolean.FALSE;
    private LocalDateTime createdAt;
}
