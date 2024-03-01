package com.halima.friendservice.controller;



import com.halima.friendservice.model.entities.Friend;
import com.halima.friendservice.model.entities.FriendRequest;
import com.halima.friendservice.service.FriendRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friend-requests")
public class FriendRequestController {

    @Autowired
    private FriendRequestService friendRequestService;
    @GetMapping
    public List<Friend> getAllFriends() {
        return friendRequestService.getAllFriends();
    }


    @PostMapping("/user/{userId}")
    public FriendRequest createFriendRequest(@RequestHeader("id") String userId, @PathVariable Long friendId    ) {
        return friendRequestService.createFriendRequest(userId, friendId);
    }

    @PutMapping("/{requestId}/accept")
    public FriendRequest acceptFriendRequest(@PathVariable Long requestId) {
        return friendRequestService.acceptFriendRequest(requestId);
    }

    @PutMapping("/{requestId}/reject")
    public FriendRequest rejectFriendRequest(@PathVariable Long requestId) {
        return friendRequestService.rejectFriendRequest(requestId);
    }
}

