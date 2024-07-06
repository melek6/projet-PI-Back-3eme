package tn.esprit.projetPI.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.projetPI.dto.ChatMessageDTO;
import tn.esprit.projetPI.dto.DtoMapper;
import tn.esprit.projetPI.models.ChatMessage;
import tn.esprit.projetPI.models.Project;
import tn.esprit.projetPI.models.User;
import tn.esprit.projetPI.repository.ChatMessageRepository;
import tn.esprit.projetPI.repository.ProjectRepository;
import tn.esprit.projetPI.repository.UserRepository;
import tn.esprit.projetPI.services.IChatMessageService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ChatMessageService implements IChatMessageService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private DtoMapper dtoMapper;

    @Override
    public List<ChatMessageDTO> getMessages(Long senderId, Long recipientId) {
        List<ChatMessage> messages = chatMessageRepository.findBySenderIdAndRecipientId(senderId, recipientId);
        return messages.stream().map(dtoMapper::toChatMessageDTO).collect(Collectors.toList());
    }

    @Override
    public ChatMessageDTO sendMessage(ChatMessageDTO chatMessageDTO) {
        ChatMessage chatMessage = dtoMapper.toChatMessage(chatMessageDTO);
        User sender = userRepository.findById(chatMessageDTO.getSenderId())
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        User recipient = userRepository.findById(chatMessageDTO.getRecipientId())
                .orElseThrow(() -> new RuntimeException("Recipient not found"));

        chatMessage.setSender(sender);
        chatMessage.setRecipient(recipient);

        ChatMessage savedMessage = chatMessageRepository.save(chatMessage);
        return dtoMapper.toChatMessageDTO(savedMessage);
    }

    @Override
    public List<ChatMessageDTO> getConversationsForUser(Long userId) {
        List<ChatMessage> messages = chatMessageRepository.findBySenderIdOrRecipientId(userId, userId);
        return messages.stream().map(dtoMapper::toChatMessageDTO).collect(Collectors.toList());
    }
}
