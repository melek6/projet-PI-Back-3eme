package tn.esprit.projetPI.services;

import tn.esprit.projetPI.dto.ChatMessageDTO;

import java.util.List;

public interface IChatMessageService {
    List<ChatMessageDTO> getMessages(Long senderId, Long recipientId);
    ChatMessageDTO sendMessage(ChatMessageDTO chatMessageDTO);
    List<ChatMessageDTO> getConversationsForUser(Long userId);
}
