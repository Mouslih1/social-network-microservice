package com.halima.friendservice.service;

import com.halima.friendservice.dto.FriendDto;
import com.halima.friendservice.repository.FriendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;

    public FriendDto findFriendIdsByUserId(Long userId) {

        List<Long> friendIds = friendRepository.findFriendIdsByUserId(userId);
        return FriendDto.builder()
                .userId(userId)
                .friendId(friendIds)
                .build();
    }

    public void deleteByUserIdAndFriendId(Long userId, Long friendId) {
        friendRepository.deleteByUserIdAndFriendId(userId, friendId);
    }

    public Boolean existsByUserIdAndFriendId(Long userId, Long friendId) {
        return friendRepository.existsByUserIdAndFriendId(userId, friendId);
    }

    public Long countByUserId(Long userId) {
        return friendRepository.countByUserId(userId);
    }
    public Long findFriendByUserId(Long userId, Long friendId) {
        return friendRepository.findFriendIdByUserIdAndFriendId(userId, friendId);
    }


}
