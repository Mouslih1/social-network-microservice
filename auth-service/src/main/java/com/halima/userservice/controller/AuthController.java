package com.halima.userservice.controller;

import com.halima.userservice.security.CustomUserDetails;
import com.halima.userservice.dto.LoginDTO;
import com.halima.userservice.dto.UserDTO;
import com.halima.userservice.service.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin("*")
public class AuthController {

    private final IUserService userService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/token")
    public String getToken(@RequestBody LoginDTO loginDTO)
    {
        log.info("Authenticating user {} ", loginDTO.getUsername());
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
        log.info("Authenticated user {} ", authenticate);
        if (authenticate.isAuthenticated())
        {
            log.info("Generating token for user {} ", loginDTO.getUsername());
            CustomUserDetails userDetails = (CustomUserDetails) authenticate.getPrincipal();
            Long idUser = userDetails.getId();
            String email = userDetails.getEmail();
            return userService.generateToken(idUser,loginDTO.getUsername(), email);

        } else {
            throw new RuntimeException("invalid access !");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO)
    {
        return new ResponseEntity<>(userService.save(userDTO),HttpStatus.CREATED);
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token)
    {
        userService.validateToken(token);
        return "Token is valid";
    }
}
