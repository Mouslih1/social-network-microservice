package com.halima.friendservice.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "user-service", url = "http://localhost:8083/api/v1/users")
public interface UserClient {

    @GetMapping("/exist/{userId}")
     boolean userExists(Long userId);
}
