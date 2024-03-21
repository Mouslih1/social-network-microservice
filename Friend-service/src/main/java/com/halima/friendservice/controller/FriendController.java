package com.halima.friendservice.controller;

import com.halima.friendservice.dto.FriendDto;
import com.halima.friendservice.dto.UserDTO;
import com.halima.friendservice.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friend")
@RequiredArgsConstructor
@CrossOrigin("*")
public class FriendController {

    private final FriendService friendService;


    @GetMapping("/profiles/all")
    public ResponseEntity<List<UserDTO>> getAllFriendsProfile(@RequestHeader("id") String userId) {
        return ResponseEntity.ok(friendService.getAllFriendsProfile(Long.valueOf(userId)));
    }


    // Get all friends
    @GetMapping("/all")
    public ResponseEntity<FriendDto> getFriends(@RequestHeader("id") String userId) {
        return ResponseEntity.ok(friendService.findFriendIdsByUserId(Long.valueOf(userId)));
    }

    // Get  friend by id
    @GetMapping("/{friendId}")
    public ResponseEntity<Long> getFriend(@RequestHeader("id") String userId, @PathVariable Long friendId) {

        return ResponseEntity.ok(friendService.findFriendByUserId(Long.valueOf(userId), friendId));
    }
    // Delete a friend

    @DeleteMapping("/{friendId}")
    public ResponseEntity<Void> deleteFriend(@RequestHeader("id") String userId,@PathVariable Long friendId) {
        friendService.deleteFriend(Long.valueOf(userId), friendId);
        return ResponseEntity.noContent().build();
    }
}
