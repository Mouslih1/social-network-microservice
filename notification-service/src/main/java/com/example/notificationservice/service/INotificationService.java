package com.example.notificationservice.service;

import com.example.notificationservice.dto.NotificationDto;
import com.nabilaitnacer.servicepost.dto.PostProducerDto;

import java.util.List;

public interface INotificationService {

    void sendPostCreationNotification(PostProducerDto post);
    List<NotificationDto> getUnseenNotifications(Long user);
    void markNotificationAsSeen(Long notificationId);
}
