package com.halima.friendservice.feign;

import com.halima.userservice.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "User-service",url = "http://localhost:8083/api/users")
public interface UserServiceClient {

    @GetMapping("/{userId}")
    ResponseEntity<UserDTO> getUserById(@PathVariable Long userId);

    @GetMapping
    ResponseEntity<List<UserDTO>> getAllUsers();
}


