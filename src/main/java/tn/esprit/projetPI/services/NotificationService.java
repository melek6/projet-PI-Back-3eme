package tn.esprit.projetPI.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.projetPI.dto.NotificationDTO;
import tn.esprit.projetPI.models.Notification;
import tn.esprit.projetPI.models.Proposition;
import tn.esprit.projetPI.models.User;
import tn.esprit.projetPI.repository.NotificationRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService implements INotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    @Override
    public List<NotificationDTO> getNotificationsByUserId(Long userId) {
        List<Notification> notifications = notificationRepository.findByUserId(userId);
        return notifications.stream()
                .map(notification -> new NotificationDTO(notification.getId(), notification.getMessage()))
                .collect(Collectors.toList());
    }

    @Override
    public void saveNotification(Notification notification) {
        notificationRepository.save(notification);
    }

    public void sendNewPropositionNotification(User user, String projectName) {
        String message = "You have received a new proposition for project: " + projectName;
        Notification notification = new Notification(message, user);
        saveNotification(notification);
    }

    public void createNotificationForProposition(Proposition proposition, String message) {
        Notification notification = new Notification();
        notification.setUser(proposition.getProject().getUser());
        notification.setMessage(message.replace("{projectName}", proposition.getProject().getTitle()));
        notificationRepository.save(notification);
    }

    @Override
    public void clearNotificationsByUserId(Long userId) {
        notificationRepository.deleteByUserId(userId);
    }
}
