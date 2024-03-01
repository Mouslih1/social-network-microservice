package com.halima.friendservice.service;

import com.halima.friendservice.openfeign.UserClient;
import com.halima.friendservice.repository.FriendRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final FriendRequestService friendRequestService;
    private final UserClient userClient;
    private final FriendRepository friendRepository;
    private final ModelMapper modelMapper;










}
