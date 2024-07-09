package tn.esprit.projetPI.services;

import tn.esprit.projetPI.models.Chat;
import tn.esprit.projetPI.models.Message;
import tn.esprit.projetPI.models.User;

import java.util.List;

public interface IChatService {
    Chat addChat(Chat chat);
    List<Chat> findAllChats();
    Chat getChatById(Long id);
    List<Chat> getChatByFirstUserUsernameOrSecondUserUsername(String username);
    Chat addMessage(Message message, Long chatId);
    List<User> getAllUsers();
}
