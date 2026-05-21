package com.flareye.notificationservice.dto;

import com.flareye.notificationservice.models.NotificationType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateNotificationRequest {
    private String userId;
    private String title;
    private String body;
    private NotificationType type;
    private String referenceId;
}
