package com.flareye.notificationservice.controllers;

import com.flareye.notificationservice.dto.CreateNotificationRequest;
import com.flareye.notificationservice.dto.NotificationDTO;
import com.flareye.notificationservice.models.Notification;
import com.flareye.notificationservice.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping
    public ResponseEntity<NotificationDTO> create(@RequestBody CreateNotificationRequest request) {
        Notification notification = notificationService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(notificationService.toDTO(notification));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationDTO>> getByUser(@PathVariable String userId) {
        return ResponseEntity.ok(notificationService.findByUserId(userId));
    }

    @GetMapping("/user/{userId}/unread")
    public ResponseEntity<List<NotificationDTO>> getUnread(@PathVariable String userId) {
        return ResponseEntity.ok(notificationService.findUnreadByUserId(userId));
    }

    @GetMapping("/user/{userId}/unread/count")
    public ResponseEntity<Map<String, Long>> countUnread(@PathVariable String userId) {
        return ResponseEntity.ok(Map.of("count", notificationService.countUnread(userId)));
    }

    @PatchMapping("/{id}/read")
    public ResponseEntity<NotificationDTO> markAsRead(@PathVariable Long id) {
        Optional<Notification> notification = notificationService.markAsRead(id);
        return notification.map(n -> ResponseEntity.ok(notificationService.toDTO(n)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/user/{userId}/read-all")
    public ResponseEntity<Void> markAllAsRead(@PathVariable String userId) {
        notificationService.markAllAsRead(userId);
        return ResponseEntity.noContent().build();
    }
}
