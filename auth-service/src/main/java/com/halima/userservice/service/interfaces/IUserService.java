package com.halima.userservice.service.interfaces;

import com.halima.userservice.dto.UserDTO;

import java.util.List;

public interface IUserService {

    UserDTO save(UserDTO userDTO);
    String generateToken(Long id, String username, String email);
    void validateToken(String token);
}
