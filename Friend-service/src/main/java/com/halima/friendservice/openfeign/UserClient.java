package com.halima.friendservice.openfeign;

import com.halima.friendservice.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "http://localhost:8083/api/v1/users", fallback = UserClientFallback.class)
public interface UserClient {

    @GetMapping("/exist/{userId}")
     boolean userExists(@PathVariable Long userId);

    @GetMapping("/{userId}")
    ResponseEntity<UserDTO> getUserById(@PathVariable Long userId);
}
