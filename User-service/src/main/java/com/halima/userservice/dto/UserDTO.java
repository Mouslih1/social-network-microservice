package com.halima.userservice.dto;

import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    @NotNull(message = "ID cannot be null")
    private Long id;

    @NotBlank(message = "Username cannot be blank")
    @Size(min =  3, max =  20, message = "Username must be between  3 and  20 characters")
    private String username;

    @NotBlank(message = "Password cannot be blank")
    @Size(min =  5, max =  20, message = "Password must be between  8 and  20 characters")
    private String password;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "First name cannot be blank")
    private String firstname;

    @NotBlank(message = "Last name cannot be blank")
    private String lastname;

    @Past(message = "Date of Bith must be in the past")
    private Date dateOfBirth;

    @NotBlank(message = "Country cannot be blank")
    @Size(min =  2, max =  50, message = "Country name must be between  2 and  50 characters")
    private String country;
    private LocalDateTime createdAt;
}
