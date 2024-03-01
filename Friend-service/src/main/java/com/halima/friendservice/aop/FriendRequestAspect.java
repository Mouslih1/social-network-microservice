package com.halima.friendservice.aop;

import com.halima.friendservice.exception.FriendRequestException;
import com.halima.friendservice.exception.UserNotFoundException;
import com.halima.friendservice.model.entities.FriendRequest;
import com.halima.friendservice.openfeign.UserClient;
import com.halima.friendservice.service.FriendRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class FriendRequestAspect {

    private final FriendRequestService friendRequestService;
    private final UserClient userClient;
    @Before("execution(* com.halima.friendservice.service.*.*(..))" +
            " && !execution(* com.halima.friendservice.service.FriendRequestService.getFriendRequest(..))"
    + " && !execution(* com.halima.friendservice.service.FriendRequestService.rejectFriendRequest(..))"
      + " && !execution(* com.halima.friendservice.service.FriendRequestService.acceptFriendRequest(..))"
    )
    public void beforeAnyMethod(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();

        Long loggedInUserId = (Long) args[0];
        Long senderId = null;
        log.info("loggedInUserId: {}", loggedInUserId);
        if(args.length > 1){
             senderId = (Long) args[1];
            if (!userClient.userExists(senderId)) {
                throwUserNotFoundException(senderId);
            }
            log.info("senderId: {}", senderId);
        }




        Optional<FriendRequest> request = friendRequestService.getFriendRequest(loggedInUserId, senderId);

        if( request.isPresent()  && !Objects.equals(request.get().getUserIdSender(), loggedInUserId)){
            throwFriendRequestException("Vous n'êtes pas autorisé à accepter cette demande");
        }
         checkIfUserIsSendingRequestToSelf(loggedInUserId, senderId);



    }
    private void throwUserNotFoundException(Long friendId) {
        throw new UserNotFoundException(String.format("User with id %s does not exist", friendId));
    }
    private void throwFriendRequestException(String message) {
        throw new FriendRequestException(message);
    }


    public void checkIfUserIsSendingRequestToSelf(Long loggedInUserId, Long senderId) {
        if (loggedInUserId.equals(senderId)) {
            throwFriendRequestException("Vous ne pouvez pas envoyer une demande à vous-même");
        }
    }
}
