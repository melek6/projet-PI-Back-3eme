package tn.esprit.projetPI.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.projetPI.models.Notification;
import tn.esprit.projetPI.models.Proposition;
import tn.esprit.projetPI.models.User;
import tn.esprit.projetPI.repository.NotificationRepository;

import java.util.List;

@Service
public class NotificationService implements INotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    @Override
    public List<Notification> getNotificationsByUserId(Long userId) {
        return notificationRepository.findByUserId(userId);
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
}
