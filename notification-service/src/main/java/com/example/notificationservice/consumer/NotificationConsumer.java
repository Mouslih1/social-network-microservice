package com.example.notificationservice.consumer;

import com.example.notificationservice.service.INotificationService;
import com.nabilaitnacer.servicepost.dto.PostProducerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationConsumer {

    private final INotificationService iNotificationService;

    @KafkaListener(topics = "post-topic", groupId = "myGroup")
    public void sendNotifForPostToAllFriend(PostProducerDto postNotif)
    {
        iNotificationService.sendPostCreationNotification(postNotif);
        //log.info(String.format("Consuming the message from post-topic topic: %s", postNotif));
    }
}
