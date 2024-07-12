package tn.esprit.projetPI.services;

import tn.esprit.projetPI.models.Chat;
import tn.esprit.projetPI.models.Message;

import java.util.List;

public interface IChatService {
    Chat addChat(Chat chat);
    List<Chat> findAllChats();
    Chat getChatById(Long id);
    List<Chat> getChatByFirstUserUsername(String username);
    List<Chat> getChatBySecondUserUsername(String username);
    List<Chat> getChatByFirstUserUsernameOrSecondUserUsername(String username);
    Chat addMessage(Message message, Long chatId);
}
