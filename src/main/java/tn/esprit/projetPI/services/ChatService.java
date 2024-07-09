package tn.esprit.projetPI.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.projetPI.models.Chat;
import tn.esprit.projetPI.models.Message;
import tn.esprit.projetPI.models.User;
import tn.esprit.projetPI.repository.ChatRepository;
import tn.esprit.projetPI.repository.UserRepository;

import java.util.List;

@Service
public class ChatService implements IChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Chat addChat(Chat chat) {
        User firstUser = userRepository.findByUsername(chat.getFirstUser().getUsername())
                .orElseThrow(() -> new RuntimeException("User not found: " + chat.getFirstUser().getUsername()));
        User secondUser = userRepository.findByUsername(chat.getSecondUser().getUsername())
                .orElseThrow(() -> new RuntimeException("User not found: " + chat.getSecondUser().getUsername()));

        chat.setFirstUser(firstUser);
        chat.setSecondUser(secondUser);
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

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
