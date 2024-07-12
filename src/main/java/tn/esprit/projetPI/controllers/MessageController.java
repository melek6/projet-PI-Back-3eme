package tn.esprit.projetPI.controllers;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.projetPI.dto.MessageDTO;
import tn.esprit.projetPI.dto.UserDTO;
import tn.esprit.projetPI.models.Message;
import tn.esprit.projetPI.models.User;
import tn.esprit.projetPI.services.IMessageService;
import tn.esprit.projetPI.services.UserServiceint;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private IMessageService messageService;

    @Autowired
    private UserServiceint userService;

    @PostMapping("/send")
    public MessageDTO sendMessage(@RequestParam Long userFromId, @RequestParam Long userToId, @RequestParam String text) {
        return messageService.sendMessage(userFromId, userToId, text);
    }

    @GetMapping("/from/{userFromId}")
    public List<MessageDTO> getMessagesFromUser(@PathVariable Long userFromId) {
        return messageService.getMessagesFromUser(userFromId);
    }

    @GetMapping("/to/{userToId}")
    public List<MessageDTO> getMessagesToUser(@PathVariable Long userToId) {
        return messageService.getMessagesToUser(userToId);
    }

    @GetMapping("/users")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsersDTO();
    }
}
