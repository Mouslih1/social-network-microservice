package com.nabilaitnacer.feedsservice.client;

import com.nabilaitnacer.feedsservice.dto.UserRelationDto;
import com.nabilaitnacer.feedsservice.exception.NotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserRelationClientFallback implements UserRelationClient{
    @Override
    public UserRelationDto getUserRelation(Long userId) {
        throw new NotFoundException("The relations of that user not found"+userId);
    }
}
