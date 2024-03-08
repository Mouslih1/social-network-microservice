package com.nabilaitnacer.servicepost.producer;

import com.nabilaitnacer.servicepost.dto.PostProducerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostProducer {

    private final KafkaTemplate<String, PostProducerDto> kafkaTemplate;

    public void sendMessage(PostProducerDto post)
    {
        Message<PostProducerDto> message = MessageBuilder
                .withPayload(post)
                .setHeader(KafkaHeaders.TOPIC, "post-topic")
                .build();
        kafkaTemplate.send(message);
    }
}
