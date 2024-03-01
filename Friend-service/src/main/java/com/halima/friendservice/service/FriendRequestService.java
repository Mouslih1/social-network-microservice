package com.halima.friendservice.service;

import com.halima.friendservice.dto.FriendRequestDto;
import com.halima.friendservice.exception.FriendRequestException;
import com.halima.friendservice.exception.UserNotFoundException;
import com.halima.friendservice.model.entities.Friend;
import com.halima.friendservice.model.entities.FriendRequest;
import com.halima.friendservice.model.enums.Status;
import com.halima.friendservice.openfeign.UserClient;
import com.halima.friendservice.repository.FriendRepository;
import com.halima.friendservice.repository.FriendRequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FriendRequestService {
    private final ModelMapper modelMapper;

    private final FriendRequestRepository friendRequestRepository;

    private final FriendRepository friendRepository;



    public FriendRequestDto createFriendRequest(Long userIdSender, Long friendId) {
        log.info("Creating friend request for user {} and friend {}", userIdSender, friendId);

        Optional<FriendRequest> request = getFriendRequest(userIdSender, friendId);
        FriendRequest friendRequest = FriendRequest.builder()
                .userIdSender(userIdSender)
                .friendId(friendId)
                .build();
        //TODO Send Notification to the user
        return modelMapper.map(friendRequestRepository.save(friendRequest), FriendRequestDto.class);
    }

    public Optional<FriendRequest> getFriendRequest(Long userIdSender, Long friendId) {
        Optional<FriendRequest> request = friendRequestRepository.findByUserIdSenderAndFriendId(userIdSender, friendId);
        return request;
    }


    @Transactional
    public FriendRequestDto acceptFriendRequest(Long userId, Long requestId) {
        log.info("Accepting friend request with id {}", requestId);

        Optional<FriendRequest> friendRequest = getFriendRequest(userId, requestId);

        friendRequest.get().setStatus(Status.ACCEPTED);
        Friend friend = Friend.builder()
                .userIdSender(friendRequest.get().getUserIdSender())
                .friendId(friendRequest.get().getFriendId())
                .build();
        friendRepository.save(friend);
        friendRequestRepository.save(friendRequest.get());

        return modelMapper.map(friendRequest, FriendRequestDto.class);
    }

    public FriendRequestDto rejectFriendRequest(Long userId, Long requestId) {
        log.info("Rejecting friend request with id {}", requestId);
        Optional<FriendRequest> friendRequest = friendRequestRepository.findById(requestId);
        friendRequest.get().setStatus(Status.REJECTED);
        return  modelMapper.map(friendRequestRepository.save(friendRequest.get()), FriendRequestDto.class);
    }

    public List<FriendRequestDto> getAllFriendRequestByIdSender(Long idSender) {
        log.info("Getting all friend request for user with id {}", idSender);
         return friendRequestRepository.findByUserIdSender(idSender).stream().map(element -> modelMapper.map(element, FriendRequestDto.class)).toList();
    }

    public List<FriendRequestDto> getAllFriendRequestByIdSenderAndStatus(Long idSender, Status status) {
        log.info("Getting all friend request for user with id {} and status {}", idSender, status);
        return friendRequestRepository.findByUserIdSenderAndStatus(idSender, status).stream().map(element -> modelMapper.map(element, FriendRequestDto.class)).toList();
    }



}
