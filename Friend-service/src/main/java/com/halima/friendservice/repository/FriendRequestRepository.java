package com.halima.friendservice.repository;

import com.halima.friendservice.model.entities.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRequestRepository extends JpaRepository<FriendRequest,Long> {

    
}
