package com.halima.friendservice.openfeign;

import com.halima.friendservice.dto.UserDTO;
import com.halima.friendservice.exception.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class UserClientFallback implements UserClient{
    @Override
    public boolean userExists(Long userId) {
        return false;
    }

    @Override
    public ResponseEntity<UserDTO> getUserById(Long userId) {
        throw new UserNotFoundException("Error lors de recuperation de user.");
    }
}
