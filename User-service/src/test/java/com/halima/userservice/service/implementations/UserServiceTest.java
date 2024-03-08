package com.halima.userservice.service.implementations;

import com.halima.userservice.dto.UserDTO;
import com.halima.userservice.model.User;
import com.halima.userservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createUser() {
        UserDTO userDTO = UserDTO.builder()
                .id(1L)
                .username("test")
                .email("elamri@gmail.com")
                .password("password1")
                .firstname("Elamri")
                .lastname("Halima")
                .country("Morocco")
                .build();

        User user = User.builder()
                .id(1L)
                .username("test")
                .email("elamri@gmail.com")
                .password("password1")
                .firstname("Elamri")
                .lastname("Halima")
                .country("Morocco")
                .build();

        when(modelMapper.map(userDTO, User.class)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(modelMapper.map(user, UserDTO.class)).thenReturn(userDTO);

//        UserDTO createdUserDto = userService.save(userDTO);

//        assertEquals(userDTO, createdUserDto);

    }
//    @Test
//    void editUser() {
//        Long userId = 1L;
//        UserDTO userDto = UserDTO.builder()
//                .id(userId)
//                .username("test")
//                .email("elamri@gmail.com")
//                .password("password1")
//                .firstname("Elamri")
//                .lastname("Halima")
//                .country("Morocco")
//                .build();
//
//        User existingUser = User.builder()
//                .id(userId)
//                .username("test")
//                .email("elamri@gmail.com")
//                .password("password1")
//                .firstname("Elamri")
//                .lastname("Halima")
//                .country("Morocco")
//                .build();
//
//        when(modelMapper.map(userDto, User.class)).thenReturn(existingUser);
//        when(userRepository.save(existingUser)).thenReturn(existingUser);
//        when(modelMapper.map(existingUser, UserDTO.class)).thenReturn(userDto);
//
//        UserDTO editedUserDto = userService.updateUser(userDto);
//
//        assertNotNull(editedUserDto);
//        assertEquals(userDto, editedUserDto);
//        // Add any additional assertions for the edited user if necessary
//    }


    @Test
    void deleteUser() {
        Long userId = 1L;
        User user = User.builder()
                .id(userId)
                .username("test")
                .email("elamri@gmail.com")
                .password("password1")
                .firstname("Elamri")
                .lastname("Halima")
                .country("Morocco")
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        boolean deleted;
        userService.delete(userId);

//        assertTrue(deleted);
        // Add any additional assertions for the deleted user if necessary
    }



    @Test
    void getAllUsers() {
        List<User> users = new ArrayList<>();
        users.add(User.builder().id(1L).username("user1").email("user1@example.com").build());
        users.add(User.builder().id(2L).username("user2").email("user2@example.com").build());

        when(userRepository.findAll()).thenReturn(users);

        when(modelMapper.map(users.get(0), UserDTO.class)).thenReturn(UserDTO.builder().id(1L).username("user1").email("user1@example.com").build());
        when(modelMapper.map(users.get(1), UserDTO.class)).thenReturn(UserDTO.builder().id(2L).username("user2").email("user2@example.com").build());

        List<UserDTO> userDtos = userService.getAll();

        assertEquals(2, userDtos.size());

    }

}