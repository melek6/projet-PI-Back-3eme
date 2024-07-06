package tn.esprit.projetPI.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.projetPI.dto.ChatMessageDTO;
import tn.esprit.projetPI.services.IChatMessageService;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
@CrossOrigin(origins = "http://localhost:4200")
public class ChatMessageController {

    @Autowired
    private IChatMessageService chatMessageService;

    @PostMapping("/send")
    public ChatMessageDTO sendMessage(@RequestBody ChatMessageDTO chatMessageDTO) {
        return chatMessageService.sendMessage(chatMessageDTO);
    }

    @GetMapping("/{senderId}/{recipientId}")
    public List<ChatMessageDTO> getMessages(@PathVariable Long senderId, @PathVariable Long recipientId) {
        return chatMessageService.getMessages(senderId, recipientId);
    }

    @GetMapping("/conversations/{userId}")
    public List<ChatMessageDTO> getConversationsForUser(@PathVariable Long userId) {
        return chatMessageService.getConversationsForUser(userId);
    }
}
