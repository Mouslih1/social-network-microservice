package com.example.notificationservice.client;

import com.example.notificationservice.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(url = "http://localhost:8078/api/friend", name = "friend-service")
public interface FriendClient {

    @GetMapping("/profiles/all")
    ResponseEntity<List<UserDTO>> getAllFriendsProfile(@RequestHeader("id") String userId);
}
