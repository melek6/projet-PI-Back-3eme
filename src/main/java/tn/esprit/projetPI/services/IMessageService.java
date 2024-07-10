package tn.esprit.projetPI.services;

import tn.esprit.projetPI.dto.MessageDTO;
import tn.esprit.projetPI.models.Message;

import java.util.List;

public interface IMessageService {

    MessageDTO sendMessage(Long userFromId, Long userToId, String text);
        List<MessageDTO> getMessagesFromUser(Long userFromId);
        List<MessageDTO> getMessagesToUser(Long userToId);
    }

