package tn.esprit.projetPI.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import tn.esprit.projetPI.dto.NotificationDTO;
import tn.esprit.projetPI.models.Notification;
import tn.esprit.projetPI.models.User;
import tn.esprit.projetPI.repository.UserRepository;
import tn.esprit.projetPI.services.INotificationService;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "http://localhost:4200")
public class NotificationController {

    @Autowired
    private INotificationService notificationService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<NotificationDTO>> getNotifications() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
        List<NotificationDTO> notifications = notificationService.getNotificationsByUserId(user.getId());
        return ResponseEntity.ok(notifications);
    }

    @DeleteMapping
    public ResponseEntity<Void> clearNotifications() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
        notificationService.clearNotificationsByUserId(user.getId());
        return ResponseEntity.ok().build();
    }
}
