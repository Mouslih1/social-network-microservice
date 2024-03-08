
package com.halima.userservice.service.implementations;

import com.halima.userservice.dto.UserDTO;
import com.halima.userservice.exception.NotFoundException;
import com.halima.userservice.model.User;
import com.halima.userservice.repository.UserRepository;
import com.halima.userservice.service.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService implements IUserService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private static final String USER_NOT_FOUND = "User not found with this id : ";

    @Override
    public UserDTO update(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND + id));
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setCountry(userDTO.getCountry());
        user.setDateOfBirth(userDTO.getDateOfBirth());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setLastname(userDTO.getLastname());
        user.setFirstname(userDTO.getFirstname());
        User userUpdated = userRepository.save(user);
        return modelMapper.map(userUpdated, UserDTO.class);
    }

    @Override
    public UserDTO getById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND + id));
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public List<UserDTO> getAll() {
        List<User> users = userRepository.findAll();
        return users
                .stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .toList();
    }


    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean userExists(Long userId) {
        return userRepository.existsById(userId);
    }
}
