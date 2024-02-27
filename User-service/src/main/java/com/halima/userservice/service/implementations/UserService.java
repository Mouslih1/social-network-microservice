
package com.halima.userservice.service.implementations;

import com.halima.userservice.dto.UserDTO;
import com.halima.userservice.model.User;
import com.halima.userservice.repository.UserRepository;
import com.halima.userservice.service.interfaces.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    public UserDTO convertToDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public User convertToEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }
    @Override
    @Transactional
    public UserDTO getUserById(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        return convertToDTO(user);
    }
   @Override
   @Transactional
   public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    @Override
    @Transactional
    public void saveUser(UserDTO userDTO) {
        User user = convertToEntity(userDTO);
        userRepository.save(user);
    }


    @Override
    @Transactional
    public UserDTO updateUtilisateur(UserDTO userDTO) {
        try {
            Optional<User> utilisateur = userRepository.findById(userDTO.getId());
            if (utilisateur.isPresent()) {
                modelMapper.map(userDTO, utilisateur.get());
               userRepository.save(utilisateur.get());
                return convertToDTO(utilisateur.get());
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Error updating utilisateur", e);
        }
    }
    @Override
    @Transactional
    public void deleteUser(Long userId) {
        try {
            userRepository.deleteById(userId);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting utilisateur", e);
        }
    }

}
