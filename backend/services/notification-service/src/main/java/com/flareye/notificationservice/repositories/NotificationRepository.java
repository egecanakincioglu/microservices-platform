package com.flareye.notificationservice.repositories;

import com.flareye.notificationservice.models.Notification;
import com.flareye.notificationservice.models.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserId(String userId);
    List<Notification> findByUserIdAndReadFalse(String userId);
    List<Notification> findByUserIdAndType(String userId, NotificationType type);
    long countByUserIdAndReadFalse(String userId);
}
