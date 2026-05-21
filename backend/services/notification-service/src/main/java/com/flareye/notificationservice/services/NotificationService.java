package com.flareye.notificationservice.services;

import com.flareye.notificationservice.dto.CreateNotificationRequest;
import com.flareye.notificationservice.dto.NotificationDTO;
import com.flareye.notificationservice.models.Notification;
import com.flareye.notificationservice.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public Notification create(CreateNotificationRequest request) {
        Notification notification = new Notification();
        notification.setUserId(request.getUserId());
        notification.setTitle(request.getTitle());
        notification.setBody(request.getBody());
        notification.setType(request.getType());
        notification.setReferenceId(request.getReferenceId());
        return notificationRepository.save(notification);
    }

    public List<NotificationDTO> findByUserId(String userId) {
        return notificationRepository.findByUserId(userId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<NotificationDTO> findUnreadByUserId(String userId) {
        return notificationRepository.findByUserIdAndReadFalse(userId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public long countUnread(String userId) {
        return notificationRepository.countByUserIdAndReadFalse(userId);
    }

    public Optional<Notification> markAsRead(Long id) {
        return notificationRepository.findById(id).map(n -> {
            n.setRead(true);
            n.setReadAt(LocalDateTime.now());
            return notificationRepository.save(n);
        });
    }

    public void markAllAsRead(String userId) {
        List<Notification> unread = notificationRepository.findByUserIdAndReadFalse(userId);
        unread.forEach(n -> {
            n.setRead(true);
            n.setReadAt(LocalDateTime.now());
        });
        notificationRepository.saveAll(unread);
    }

    public NotificationDTO toDTO(Notification notification) {
        return NotificationDTO.builder()
                .id(notification.getId())
                .userId(notification.getUserId())
                .title(notification.getTitle())
                .body(notification.getBody())
                .type(notification.getType())
                .read(notification.getRead())
                .createdAt(notification.getCreatedAt())
                .readAt(notification.getReadAt())
                .build();
    }
}
