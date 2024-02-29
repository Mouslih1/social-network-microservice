package com.halima.userservice.controller;

import com.halima.userservice.dto.UserDTO;
import com.halima.userservice.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId) {
        UserDTO userDTO = userService.getUserById(userId);
        if (userDTO != null) {
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> userDTOList = userService.getAllUsers();
        return ResponseEntity.ok(userDTOList);
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody UserDTO userDTO) {
        userService.saveUser(userDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long userId, @RequestBody UserDTO userDTO) {
        userDTO.setId(userId);
        UserDTO updatedUserDTO = userService.updateUser(userDTO);
        if (updatedUserDTO != null) {
            return ResponseEntity.ok(updatedUserDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

}
