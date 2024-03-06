package com.nabilaitnacer.feedsservice.client;

import com.nabilaitnacer.feedsservice.dto.FriendShip;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "friendship-service", fallback = FriendshipClientFallback.class)
public interface  FriendshipServiceClient {

    @GetMapping("/friendship-service/friendship/{userId}")
    List<FriendShip> getFriends(@PathVariable("userId") Long userId);
}
