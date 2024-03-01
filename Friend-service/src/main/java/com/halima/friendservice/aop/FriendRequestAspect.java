package com.halima.friendservice.aop;

import com.halima.friendservice.exception.FriendRequestException;
import com.halima.friendservice.exception.UserNotFoundException;
import com.halima.friendservice.model.entities.FriendRequest;
import com.halima.friendservice.openfeign.UserClient;
import com.halima.friendservice.service.FriendRequestService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Aspect
@Component
@RequiredArgsConstructor
public class FriendRequestAspect {

    private final FriendRequestService friendRequestService;
    private final UserClient userClient;
    @Before("execution(* com.halima.friendservice.service.*.*(..))")
    public void beforeAnyMethod(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Long loggedInUserId = (Long) args[0];
        Long senderId = (Long) args[1];
        if (!userClient.userExists(senderId)) {
            throwUserNotFoundException(senderId);
        }

        Optional<FriendRequest> request = friendRequestService.getFriendRequest(loggedInUserId, senderId);

        if( request.isPresent() && !Objects.equals(request.get().getUserIdSender(), loggedInUserId)){
            throwFriendRequestException("Vous n'êtes pas autorisé à accepter cette demande");
        }
        checkIfUserIsSendingRequestToSelf(loggedInUserId, senderId);
        checkFriendRequestStatusAndThrowException(request);
    }
    private void throwUserNotFoundException(Long friendId) {
        throw new UserNotFoundException(String.format("User with id %s does not exist", friendId));
    }
    private void throwFriendRequestException(String message) {
        throw new FriendRequestException(message);
    }

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
    public void checkIfUserIsSendingRequestToSelf(Long loggedInUserId, Long senderId) {
        if (loggedInUserId.equals(senderId)) {
            throwFriendRequestException("Vous ne pouvez pas envoyer une demande à vous-même");
        }
    }
}
