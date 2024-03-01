package com.halima.friendservice.controller;

import com.halima.friendservice.dto.FriendDto;
import com.halima.friendservice.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/friend")
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;


    // Delete a friend
    @DeleteMapping("/{friendId}")
    public ResponseEntity<Void> deleteFriend(@RequestHeader("id") String userId, Long friendId) {
        friendService.deleteByUserIdAndFriendId(Long.valueOf(userId), friendId);
        return ResponseEntity.noContent().build();
    }


    // Get all friends
    @GetMapping
    public ResponseEntity<FriendDto> getFriends(@RequestHeader("id") String userId) {
        return ResponseEntity.ok(friendService.findFriendIdsByUserId(Long.valueOf(userId)));
    }

    // Get  friend by id
    @GetMapping("/{friendId}")
    public ResponseEntity<Long> getFriend(@RequestHeader("id") String userId, @PathVariable Long friendId) {
        return ResponseEntity.ok(friendService.findFriendByUserId(Long.valueOf(userId)));
    }

}
