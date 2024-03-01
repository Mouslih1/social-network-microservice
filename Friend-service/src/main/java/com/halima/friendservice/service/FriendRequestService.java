package com.halima.friendservice.service;

import com.halima.friendservice.model.entities.Friend;
import com.halima.friendservice.model.entities.FriendRequest;
import com.halima.friendservice.repository.FriendRepository;
import com.halima.friendservice.repository.FriendRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
    public class FriendRequestService {

        @Autowired
        private FriendRequestRepository friendRequestRepository;
        @Autowired
         private FriendRepository friendRepository;

    public FriendRequest createFriendRequest(String userIdSender, Long friendId) {
            FriendRequest friendRequest = new FriendRequest();
            friendRequest.setUserIdSender(Long.valueOf(userIdSender));
            friendRequest.setFriendId(friendId);
            friendRequest.setStatus("PENDING");
            friendRequest.setCreatedAt(new Date());
            return friendRequestRepository.save(friendRequest);
        }



    public FriendRequest acceptFriendRequest(Long requestId) {
        FriendRequest friendRequest = friendRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Demande d'amis introuvable"));
        friendRequest.setStatus("ACCEPTED");
        friendRequest.setCreatedAt(new Date());
        friendRequest = friendRequestRepository.save(friendRequest);

        Friend friend = new Friend();
        friend.setUserIdSender(friendRequest.getUserIdSender());
        friend.setFriendId(friendRequest.getFriendId());
        friendRepository.save(friend);

        return friendRequest;
    }


    public List<Friend> getAllFriends() {
        return friendRepository.findAll();
    }

        public FriendRequest rejectFriendRequest(Long requestId) {
            FriendRequest friendRequest = friendRequestRepository.findById(requestId)
                    .orElseThrow(() -> new RuntimeException("Demande d'amis introuvable"));
            friendRequest.setStatus("REJECTED");
            friendRequest.setCreatedAt(new Date());
            return friendRequestRepository.save(friendRequest);
        }
    }
