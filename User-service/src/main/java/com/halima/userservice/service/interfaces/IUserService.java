package com.halima.userservice.service.interfaces;

import com.halima.userservice.dto.UserDTO;

import java.util.List;

public interface IUserService {

    UserDTO getById(Long userId);

    List<UserDTO> getAll();

    UserDTO update(Long id,UserDTO userDTO);

    void delete(Long userId);

    boolean userExists(Long userId);
}
