package com.halima.friendservice.repository;


import com.halima.friendservice.model.entities.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    Collection<Object> findAllByUserIdSender(Long userId);

}
