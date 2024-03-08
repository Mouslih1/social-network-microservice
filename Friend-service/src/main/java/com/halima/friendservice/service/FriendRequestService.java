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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FriendRequestService {
    private final ModelMapper modelMapper;

    private final FriendRequestRepository friendRequestRepository;

    private final FriendRepository friendRepository;
    @Qualifier("com.halima.friendservice.openfeign.UserClient")
    private final UserClient userClient;



    public FriendRequestDto createFriendRequest(Long userIdSender, Long friendId) {
        log.info("Creating friend request for user {} and friend {}", userIdSender, friendId);
        if(!userClient.userExists(friendId)) throwUserNotFoundException(friendId);

        if(friendRepository.existsByUserIdAndFriendId(userIdSender, friendId))  throwFriendRequestException("Vous êtes déjà amis");
        checkIfUserIsSendingRequestToSelf(userIdSender, friendId);
        checkFriendRequestStatusAndThrowException(getFriendRequest(userIdSender, friendId));
        if(friendRequestRepository.findByFriendIdAndUserIdSender(userIdSender, friendId).isPresent()) throwFriendRequestException("Demande d'amis déjà envoyée");
        FriendRequest friendRequest = FriendRequest.builder()
                .userIdSender(userIdSender)
                .friendId(friendId)
                .createdAt(LocalDateTime.now())
                .status(Status.PENDING)
                .updatedAt(LocalDateTime.now())
                .build();
        //TODO Send Notification to the user
        return modelMapper.map(friendRequestRepository.save(friendRequest), FriendRequestDto.class);
    }

    public Optional<FriendRequest> getFriendRequest(Long userId, Long requestId) {
        log.info("Getting friend request for user {} and friend {}", userId, requestId);
        return friendRequestRepository.findByFriendIdAndId(userId, requestId);
    }


    @Transactional
    public FriendRequestDto acceptFriendRequest(Long userId, Long requestId) {
        log.info("Accepting friend request with id {}", requestId);

        Optional<FriendRequest> friendRequest = getFriendRequest(userId, requestId);
        log.info("Optional Status friend request with id {}", friendRequest.isPresent());
        if (!friendRequest.isPresent()) {
         throwFriendRequestException("Demande d'amis n'existe pas");
        }

        if(!userClient.userExists(friendRequest.get().getUserIdSender())) throwUserNotFoundException(friendRequest.get().getFriendId());
        log.info("UserClien Status friend request with id {}", requestId);

        if(friendRepository.existsByUserIdAndFriendId(userId, friendRequest.get().getUserIdSender()))  throwFriendRequestException("Vous êtes déjà amis");
        log.info("FriendRepository Status friend request with id {}", requestId);
        if(!Objects.equals(userId, friendRequest.get().getFriendId())) throwFriendRequestException("Vous ne pouvez pas accepter une demande d'amis qui ne vous est pas destinée");
        friendRequest.get().setStatus(Status.ACCEPTED);
        friendRequest.get().setUpdatedAt(LocalDateTime.now());
        Friend friend = Friend.builder()
                .userId(userId)
                .friendId(friendRequest.get().getUserIdSender())
                .build();
        Friend friend1 = Friend.builder()
                .userId(friendRequest.get().getUserIdSender())
                .friendId(userId)
                .build();
        friendRepository.save(friend);
        friendRepository.save(friend1);
        friendRequestRepository.save(friendRequest.get());

        return modelMapper.map(friendRequest.get(), FriendRequestDto.class);
    }
    public List<FriendRequestDto> getAllFriendRequestByIdReceiver(Long idReceiver) {
        log.info("Getting all friend request for user with id {}", idReceiver);
        return friendRequestRepository.findByFriendId(idReceiver).stream().map(element -> modelMapper.map(element, FriendRequestDto.class)).toList();
    }

    public FriendRequestDto rejectFriendRequest(Long userId, Long requestId) {
        log.info("Rejecting friend request with id {}", requestId);

        Optional<FriendRequest> friendRequest = friendRequestRepository.findById(requestId);
        if(!userClient.userExists(friendRequest.get().getFriendId())) throwUserNotFoundException(friendRequest.get().getFriendId());
        checkIfUserIsSendingRequestToSelf(userId, friendRequest.get().getFriendId());

        Boolean isFriend = friendRepository.existsByUserIdAndFriendId(userId, friendRequest.get().getFriendId());
        if(isFriend){
            throwFriendRequestException("Vous êtes déjà amis");
        }
        if(!Objects.equals(userId, friendRequest.get().getFriendId())) throwFriendRequestException("Vous ne pouvez pas accepter une demande d'amis qui ne vous est pas destinée");

        friendRequest.get().setStatus(Status.REJECTED);
        friendRequest.get().setUpdatedAt(LocalDateTime.now());
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



    //TODO complet delete friend and request
    public void checkFriendRequestStatusAndThrowException(Optional<FriendRequest> request) {
        if(request.isPresent()){
            switch (request.get().getStatus()) {
                case ACCEPTED:
                    throwFriendRequestException("Vous êtes déjà amis");
                    break;
                case PENDING:
                    throwFriendRequestException("Demande d'amis déjà envoyée");
                    break;
                case REJECTED:
                    throwFriendRequestException("Demande d'amis Rejected");
                    break;
                default:
                    throwFriendRequestException("Demande d'amis n'existe pas");
                    break;
            }
        }
    }
    private void throwFriendRequestException(String message) {
        throw new FriendRequestException(message);
    }
    private void throwUserNotFoundException(Long friendId) {
        throw new UserNotFoundException(String.format("User with id %s does not exist", friendId));
    }



    public void checkIfUserIsSendingRequestToSelf(Long loggedInUserId, Long senderId) {
        log.info("Checking if user is sending request to self {} {}", loggedInUserId, senderId);
        if (loggedInUserId.equals(senderId)) {
            throwFriendRequestException("Vous ne pouvez pas envoyer une demande à vous-même");
        }
    }
}
