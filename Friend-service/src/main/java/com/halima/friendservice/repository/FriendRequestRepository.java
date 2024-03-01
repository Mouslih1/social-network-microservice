package com.halima.friendservice.repository;

import com.halima.friendservice.model.entities.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FriendRequestRepository extends JpaRepository<FriendRequest,Long> {

    Optional<FriendRequest> findByUserIdSenderAndFriendId(Long userIdSender, Long friendId);

}
