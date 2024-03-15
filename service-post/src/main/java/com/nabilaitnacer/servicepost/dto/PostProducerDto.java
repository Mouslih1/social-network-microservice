package com.nabilaitnacer.servicepost.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostProducerDto {

    private Long relatedId;
    private String body;
    private Long senderId;
    private Long receivedId;
}
