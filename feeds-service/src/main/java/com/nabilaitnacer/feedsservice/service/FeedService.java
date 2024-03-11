package com.nabilaitnacer.feedsservice.service;

import com.nabilaitnacer.feedsservice.client.FriendshipServiceClient;
import com.nabilaitnacer.feedsservice.client.InteractionServiceClient;
import com.nabilaitnacer.feedsservice.client.PostServiceClient;
import com.nabilaitnacer.feedsservice.dto.CompletReaction;
import com.nabilaitnacer.feedsservice.dto.FriendShip;
import com.nabilaitnacer.feedsservice.dto.PostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class FeedService {

    FriendshipServiceClient friendshipServiceClient;
    InteractionServiceClient interactionServiceClient;
    PostServiceClient postServiceClient;
    private final RedisTemplate<String, Object> redisTemplate;

    public HashMap<PostDto,List<CompletReaction>> getFeed(Long userId) {
        if(redisTemplate.hasKey(Long.toString(userId))){
            HashMap<PostDto, List<CompletReaction>> map = getHashMapFromRedis(Long.toString(userId));

             return map;
        }
        HashMap<PostDto,List<CompletReaction>> listHashMap = new HashMap<>();
      List<FriendShip> friendShips =  friendshipServiceClient.getFriends(userId);
      friendShips.forEach(friendShip -> {
                List<PostDto> postDto =  postServiceClient.getPostByUser(friendShip.userId);
                postDto.stream().forEach(postDto1 -> {
                    List<CompletReaction> completReaction = interactionServiceClient.getReactionsByPostId(postDto1.id);
                    listHashMap.put(postDto1,completReaction);
                })
            ;});
        saveHashMapToRedis(Long.toString(userId),listHashMap);
        return listHashMap;
    }
    public void saveHashMapToRedis(String key, HashMap<PostDto, List<CompletReaction>> map) {
        ValueOperations<String, Object> ops = this.redisTemplate.opsForValue();
        ops.set(key, map);
        redisTemplate.expire(key, 10, TimeUnit.MINUTES);
    }
    public HashMap<PostDto, List<CompletReaction>> getHashMapFromRedis(String key) {
        ValueOperations<String, Object> ops = this.redisTemplate.opsForValue();
        return (HashMap<PostDto, List<CompletReaction>>) ops.get(key);
    }

}
