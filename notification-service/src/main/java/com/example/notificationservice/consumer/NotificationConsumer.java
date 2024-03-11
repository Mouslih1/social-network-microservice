package com.example.notificationservice.consumer;

import com.nabilaitnacer.servicepost.dto.PostProducerDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationConsumer {

    @KafkaListener(topics = "post-topic", groupId = "myGroup")
    public void consumerMsg(PostProducerDto post)
    {
        log.info(String.format("Consuming the message from post-topic topic: %s", post));


    }
}
