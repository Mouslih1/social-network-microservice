package com.nabilaitnacer.servicepost.client;

import com.nabilaitnacer.servicepost.dto.inter.InteractionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "http://localhost:8070/api/v1/interactions",name = "interactions-service",fallback = InteractionFallback.class)
public interface InteractionClient {

    @GetMapping("/post/{postId}")
     ResponseEntity<InteractionDto> getInteractionsOfPost(@PathVariable Long postId);
}
