package tn.esprit.projetPI.services;

import tn.esprit.projetPI.dto.NotificationDTO;
import tn.esprit.projetPI.models.Notification;

import java.util.List;

public interface INotificationService {
    List<Notification> getAllNotifications();
    List<NotificationDTO> getNotificationsByUserId(Long userId);
    void saveNotification(Notification notification);
    void clearNotificationsByUserId(Long userId);
}
