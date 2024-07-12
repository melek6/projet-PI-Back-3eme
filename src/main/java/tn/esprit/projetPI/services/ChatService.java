package tn.esprit.projetPI.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.projetPI.models.Chat;
import tn.esprit.projetPI.models.Message;
import tn.esprit.projetPI.repository.ChatRepository;

import java.util.List;

@Service
public class ChatService implements IChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Override
    public Chat addChat(Chat chat) {
        return chatRepository.save(chat);
    }

    @Override
    public List<Chat> findAllChats() {
        return chatRepository.findAll();
    }

    @Override
    public Chat getChatById(Long id) {
        return chatRepository.findById(id).orElseThrow(() -> new RuntimeException("Chat not found"));
    }

    @Override
    public List<Chat> getChatByFirstUserUsername(String username) {
        return chatRepository.findByFirstUserUsername(username);
    }

    @Override
    public List<Chat> getChatBySecondUserUsername(String username) {
        return chatRepository.findBySecondUserUsername(username);
    }

    @Override
    public List<Chat> getChatByFirstUserUsernameOrSecondUserUsername(String username) {
        return chatRepository.findByFirstUserUsernameOrSecondUserUsername(username, username);
    }

    @Override
    public Chat addMessage(Message message, Long chatId) {
        Chat chat = getChatById(chatId);
        message.setChat(chat);
        chat.getMessageList().add(message);
        return chatRepository.save(chat);
    }
}
