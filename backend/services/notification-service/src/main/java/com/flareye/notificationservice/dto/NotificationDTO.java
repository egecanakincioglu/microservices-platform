package com.flareye.notificationservice.dto;

import com.flareye.notificationservice.models.NotificationType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDTO {
    private Long id;
    private String userId;
    private String title;
    private String body;
    private NotificationType type;
    private Boolean read;
    private LocalDateTime createdAt;
    private LocalDateTime readAt;
}
