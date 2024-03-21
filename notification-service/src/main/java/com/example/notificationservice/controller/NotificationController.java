package com.example.notificationservice.controller;

import com.example.notificationservice.dto.NotificationDto;
import com.example.notificationservice.service.INotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
@CrossOrigin("*")
public class NotificationController {

    private final INotificationService iNotificationService;

    @GetMapping("/getUnSeen")
    public ResponseEntity<List<NotificationDto>> allNotifUnSeenForUserConnect(@RequestHeader("id") String userConnect)
    {
        return new ResponseEntity<>(iNotificationService.getUnseenNotifications(Long.valueOf(userConnect)), HttpStatus.OK);
    }

    @PutMapping("/markAsSeen/{notificationId}")
    public ResponseEntity<Void> markNotificationAsSeen(@PathVariable Long notificationId)
    {
        iNotificationService.markNotificationAsSeen(notificationId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
