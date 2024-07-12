package tn.esprit.projetPI.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.projetPI.dto.DtoMapper;
import tn.esprit.projetPI.dto.MessageDTO;
import tn.esprit.projetPI.models.Message;
import tn.esprit.projetPI.models.User;
import tn.esprit.projetPI.repository.MessageRepository;
import tn.esprit.projetPI.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MessageService implements IMessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public MessageDTO sendMessage(Long userFromId, Long userToId, String text) {
        Optional<User> userFromOpt = userRepository.findById(userFromId);
        Optional<User> userToOpt = userRepository.findById(userToId);

        if (userFromOpt.isPresent() && userToOpt.isPresent()) {
            User userFrom = userFromOpt.get();
            User userTo = userToOpt.get();
            Message message = new Message(userFrom, userTo, text, LocalDateTime.now());
            Message savedMessage = messageRepository.save(message);
            return DtoMapper.toMessageDTO(savedMessage);
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }

    @Override
    public List<MessageDTO> getMessagesFromUser(Long userFromId) {
        return messageRepository.findByUserFromId(userFromId).stream()
                .map(DtoMapper::toMessageDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MessageDTO> getMessagesToUser(Long userToId) {
        return messageRepository.findByUserToId(userToId).stream()
                .map(DtoMapper::toMessageDTO)
                .collect(Collectors.toList());
    }
}