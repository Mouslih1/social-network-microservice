package com.example.notificationservice.service.impl;

import com.example.notificationservice.dto.NotificationDto;
import com.example.notificationservice.entity.Notification;
import com.example.notificationservice.repository.INotificationRepository;
import com.example.notificationservice.service.INotificationService;
import com.nabilaitnacer.servicepost.dto.PostProducerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationService implements INotificationService {

    private final INotificationRepository iNotificationRepository;
    private final ModelMapper modelMapper;

    @Override
    public void sendPostCreationNotification(Long sender, PostProducerDto post)
    {

    }

    @Override
    public List<NotificationDto> getUnseenNotifications(Long user)
    {
        return null;
    }

    @Override
    public void markNotificationAsSeen(NotificationDto notificationDto)
    {
        notificationDto.setSeen(true);
        iNotificationRepository.save(modelMapper.map(notificationDto, Notification.class));
    }
}
