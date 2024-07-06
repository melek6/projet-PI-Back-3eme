package tn.esprit.projetPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.projetPI.models.ChatMessage;
import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findBySenderIdAndRecipientId(Long senderId, Long recipientId);
    List<ChatMessage> findBySenderIdOrRecipientId(Long senderId, Long recipientId);


}
