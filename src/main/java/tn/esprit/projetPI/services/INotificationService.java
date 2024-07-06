package tn.esprit.projetPI.services;

import tn.esprit.projetPI.models.Notification;

import java.util.List;

public interface INotificationService {
    List<Notification> getAllNotifications();
    List<Notification> getNotificationsByUserId(Long userId);
    void saveNotification(Notification notification);
}
