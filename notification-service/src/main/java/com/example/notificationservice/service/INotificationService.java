package com.example.notificationservice.service;

import com.example.notificationservice.dto.NotificationDto;
import com.nabilaitnacer.servicepost.dto.PostProducerDto;

import java.util.List;

public interface INotificationService {

    void sendPostCreationNotification(Long sender, PostProducerDto post);
    List<NotificationDto> getUnseenNotifications(Long user);
    void markNotificationAsSeen(NotificationDto notification);
}
