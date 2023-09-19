package com.omada5.ergasia_omadas_5.notification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("SELECT n FROM Notification n Where n.id = ?1")
    Optional<Notification> findNotificationById(Long id);
}
