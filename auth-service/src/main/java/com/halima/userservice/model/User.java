package com.halima.userservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Setter
@Getter
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@SQLDelete(sql = "UPDATE users SET is_delete = true WHERE id=?")
@Where(clause = "is_delete = false")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;
    @Column(unique = true)
    private String email;
    private String firstname;
    private String lastname;
    private Date dateOfBirth;
    private String country;
    private LocalDateTime createdAt;
    private boolean isDelete = Boolean.FALSE;
}
