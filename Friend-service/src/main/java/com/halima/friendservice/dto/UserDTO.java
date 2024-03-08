package com.halima.friendservice.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;

    private String username;

    private String password;

    private String email;

    private String firstname;

    private String lastname;

    private Date dateOfBirth;
    private String country;
    private LocalDateTime createdAt;
}
