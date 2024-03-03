package com.halima.friendservice.repository;


import com.halima.friendservice.model.entities.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    @Query("SELECT f.friendId FROM Friend f WHERE f.userId = :userId")
    List<Long> findFriendIdsByUserId(@Param("userId") Long userId);
    void deleteByUserIdAndFriendId(Long userId, Long friendId);
    Boolean existsByUserIdAndFriendId(Long userId, Long friendId);
    Long countByUserId(Long userId);
    @Query("SELECT f.friendId FROM Friend f WHERE f.userId = :userId AND f.friendId = :friendId")
    Long findFriendIdByUserIdAndFriendId(@Param("userId") Long userId, @Param("friendId") Long friendId);

}
