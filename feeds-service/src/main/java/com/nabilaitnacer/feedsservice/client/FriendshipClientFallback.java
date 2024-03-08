package com.nabilaitnacer.feedsservice.client;

import com.nabilaitnacer.feedsservice.dto.FriendShip;
import com.nabilaitnacer.feedsservice.exception.FriendshipException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FriendshipClientFallback implements FriendshipServiceClient{
    @Override
    public List<FriendShip> getFriends(Long userId) {
        throw new FriendshipException("There was an error getting the friends of that user please retry later"+userId);
    }
}
