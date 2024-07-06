package tn.esprit.projetPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.projetPI.models.Notification;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserId(Long userId);
}
