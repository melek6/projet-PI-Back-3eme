package tn.esprit.projetPI.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.projetPI.models.Chat;
import tn.esprit.projetPI.models.Message;
import tn.esprit.projetPI.services.IChatService;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/chats")
public class ChatController {

    @Autowired
    private IChatService chatService;

    @PostMapping("/add")
    public ResponseEntity<Chat> createChat(@RequestBody Chat chat) {
        return new ResponseEntity<>(chatService.addChat(chat), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Chat>> getAllChats() {
        return new ResponseEntity<>(chatService.findAllChats(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Chat> getChatById(@PathVariable Long id) {
        return new ResponseEntity<>(chatService.getChatById(id), HttpStatus.OK);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<List<Chat>> getChatByUserUsername(@PathVariable String username) {
        return new ResponseEntity<>(chatService.getChatByFirstUserUsernameOrSecondUserUsername(username), HttpStatus.OK);
    }

    @PutMapping("/message/{chatId}")
    public ResponseEntity<Chat> addMessage(@RequestBody Message message, @PathVariable Long chatId) {
        return new ResponseEntity<>(chatService.addMessage(message, chatId), HttpStatus.OK);
    }
}
