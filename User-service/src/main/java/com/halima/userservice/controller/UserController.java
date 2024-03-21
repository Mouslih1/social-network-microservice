package com.halima.userservice.controller;

import com.halima.userservice.dto.UserDTO;
import com.halima.userservice.exception.Error;
import com.halima.userservice.service.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin("*")
public class UserController {

    private final IUserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId)
    {
        log.info("Fetching user with id: {}", userId);
        log.info("User : {}" ,userService.getById(userId));
        return new ResponseEntity<>(userService.getById(userId), HttpStatus.OK);
    }

    @GetMapping("/logged")
    public ResponseEntity<UserDTO> getProfil(@RequestHeader("id") Long id)
    {
        return new ResponseEntity<>(userService.getById(id), HttpStatus.OK);
    }

    @GetMapping("/exist/{userId}")
    public ResponseEntity<Boolean> userExists(@PathVariable Long userId)
    {
        return new ResponseEntity<>(userService.userExists(userId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers()
    {
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long userId, @RequestBody UserDTO userDTO)
    {
        return new ResponseEntity<>(userService.update(userId, userDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Error> deleteUser(@PathVariable Long userId)
    {
        Error error = new Error("User deleted successfully.");
        userService.delete(userId);
        return new ResponseEntity<>(error, HttpStatus.OK);
    }
}
