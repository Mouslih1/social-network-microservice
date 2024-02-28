package com.halima.userservice.service.interfaces;

import com.halima.userservice.dto.UserDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IUserService {

    UserDTO getUserById(Long userId);

    List<UserDTO> getAllUsers();

    UserDTO saveUser(UserDTO userDTO);

    UserDTO updateUser(UserDTO userDTO);

    @Transactional
    void deleteUser(Long userId);
}
