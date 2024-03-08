package com.nabilaitnacer.feedsservice.client;

import com.nabilaitnacer.feedsservice.dto.UserRelationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-relation-service",fallback = UserRelationClientFallback.class)
public interface UserRelationClient {
    @GetMapping("/userrelation/{userId}")
    UserRelationDto getUserRelation(@PathVariable("userId") Long userId);

}
