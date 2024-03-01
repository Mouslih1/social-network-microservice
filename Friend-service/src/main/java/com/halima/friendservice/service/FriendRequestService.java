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

@Service
@RequiredArgsConstructor
@Slf4j
public class FriendRequestService {
    private final ModelMapper modelMapper;

    private final FriendRequestRepository friendRequestRepository;

    private final FriendRepository friendRepository;
    private final UserClient userClient;

    public FriendRequestDto createFriendRequest(Long userIdSender, Long friendId) {
        log.info("Creating friend request for user {} and friend {}", userIdSender, friendId);

        if (!userClient.userExists(friendId)) {
            throwUserNotFoundException(friendId);
        }
        Optional<FriendRequest> request = friendRequestRepository.findByUserIdSenderAndFriendId(userIdSender, friendId);
        checkFriendRequestStatusAndThrowException(request);

        FriendRequest friendRequest = FriendRequest.builder()
                .userIdSender(userIdSender)
                .friendId(friendId)
                .build();
        //TODO Send Notification to the user
        return modelMapper.map(friendRequestRepository.save(friendRequest), FriendRequestDto.class);
    }



    @Transactional
    public FriendRequestDto acceptFriendRequest(Long requestId) {
        log.info("Accepting friend request with id {}", requestId);

        Optional<FriendRequest> friendRequest = friendRequestRepository.findById(requestId);
        checkFriendRequestStatusAndThrowException(friendRequest);
        friendRequest.get().setStatus(Status.ACCEPTED);
        Friend friend = Friend.builder()
                .userIdSender(friendRequest.get().getUserIdSender())
                .friendId(friendRequest.get().getFriendId())
                .build();
        friendRepository.save(friend);
        friendRequestRepository.save(friendRequest.get());

        return modelMapper.map(friendRequest, FriendRequestDto.class);
    }

    public FriendRequestDto rejectFriendRequest(Long requestId) {
        log.info("Rejecting friend request with id {}", requestId);

        Optional<FriendRequest> friendRequest = friendRequestRepository.findById(requestId);
        checkFriendRequestStatusAndThrowException(friendRequest);
        friendRequest.get().setStatus(Status.REJECTED);
        return  modelMapper.map(friendRequestRepository.save(friendRequest.get()), FriendRequestDto.class);
    }

    private void throwUserNotFoundException(Long friendId) {
        throw new UserNotFoundException(String.format("User with id %s does not exist", friendId));
    }

    private void throwFriendRequestException(String message) {
        throw new FriendRequestException(message);
    }

    private void checkFriendRequestStatusAndThrowException(Optional<FriendRequest> request) {
        if(request.isPresent()){
            switch (request.get().getStatus()) {
                case ACCEPTED:
                    throwFriendRequestException("Vous êtes déjà amis");
                    break;
                case REJECTED:
                    throwFriendRequestException("Demande d'amis Rejected");
                    break;
                default:
                    throwFriendRequestException("Demande d'amis déjà envoyée");
                    break;
            }
        }
    }
}
