package com.example.notificationservice.service.impl;

import com.example.notificationservice.client.FriendClient;
import com.example.notificationservice.dto.NotificationDto;
import com.example.notificationservice.dto.UserDTO;
import com.example.notificationservice.entity.Notification;
import com.example.notificationservice.exception.NotFoundException;
import com.example.notificationservice.repository.INotificationRepository;
import com.example.notificationservice.service.INotificationService;
import com.nabilaitnacer.servicepost.dto.PostProducerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationService implements INotificationService {

    private final INotificationRepository iNotificationRepository;
    private final ModelMapper modelMapper;
    private final FriendClient friendClient;
    private static final String NOTIFICATION_NOT_FOUND = "Notification not found with this id : ";

    @Override
    public void sendPostCreationNotification(PostProducerDto postNotif)
    {
        List<UserDTO> users = friendClient.getAllFriendsProfile(String.valueOf(postNotif.getSenderId())).getBody();

        assert users != null;
        for (UserDTO receivedFriend : users)
        {

            notifyFriends(
                    Notification
                            .builder()
                            .relatedId(postNotif.getRelatedId())
                            .message(postNotif.getBody())
                            .userReceiver(receivedFriend.getId())
                            .createdAt(LocalDateTime.now())
                            .build()
            );
        }
    }

    public void notifyFriends(Notification notification)
    {
       // log.info("friend notifiy : {}", notification);
        iNotificationRepository.save(notification);
    }

    @Override
    public List<NotificationDto> getUnseenNotifications(Long userReceiver)
    {
       List<Notification> notifications = iNotificationRepository.findByUserReceiverAndSeenIsFalse(userReceiver);
        return notifications
                .stream()
                .map((notification) -> modelMapper.map(notification, NotificationDto.class))
                .toList();
    }

    @Override
    public void markNotificationAsSeen(Long notificationId)
    {
        Notification notification = iNotificationRepository.findById(notificationId).orElseThrow(() -> new NotFoundException(NOTIFICATION_NOT_FOUND + notificationId));
        notification.setSeen(true);
        iNotificationRepository.save(notification);
    }
}
