package tn.esprit.projetPI.dto;

import org.springframework.stereotype.Component;
import tn.esprit.projetPI.models.ChatMessage;
import tn.esprit.projetPI.models.Project;
import tn.esprit.projetPI.models.Proposition;
import tn.esprit.projetPI.models.User;

@Component
public class DtoMapper {

    public ChatMessageDTO toChatMessageDTO(ChatMessage chatMessage) {
        ChatMessageDTO dto = new ChatMessageDTO();
        dto.setId(chatMessage.getId());
        dto.setSenderId(chatMessage.getSender().getId());
        dto.setRecipientId(chatMessage.getRecipient().getId());
        dto.setContent(chatMessage.getContent());
        return dto;
    }

    public ChatMessage toChatMessage(ChatMessageDTO chatMessageDTO) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setContent(chatMessageDTO.getContent());
        // Sender, Recipient, and Project will be set in the service
        return chatMessage;
    }

    public static PropositionDTO toPropositionDTO(Proposition proposition) {
        PropositionDTO dto = new PropositionDTO();
        dto.setId(proposition.getId());
        dto.setDetail(proposition.getDetail());
        dto.setAmount(proposition.getAmount());
        dto.setStatus(proposition.getStatus());

        Project project = proposition.getProject();
        if (project != null) {
            PropositionDTO.ProjectDTO projectDTO = new PropositionDTO.ProjectDTO();
            projectDTO.setId(project.getId());
            projectDTO.setTitle(project.getTitle());
            projectDTO.setDescription(project.getDescription());
            dto.setProject(projectDTO);
        }

        User user = proposition.getUser();
        if (user != null) {
            PropositionDTO.UserDTO userDTO = new PropositionDTO.UserDTO(user.getId(), user.getUsername(), user.getEmail(), project != null ? project.getId() : null);
            dto.setUser(userDTO);
        }

        return dto;
    }

    public static ProjectDTO toProjectDTO(Project project) {
        ProjectDTO dto = new ProjectDTO();
        dto.setId(project.getId());
        dto.setTitle(project.getTitle());
        dto.setDescription(project.getDescription());
        dto.setCategory(project.getCategory().name());
        dto.setSkillsRequired(project.getSkillsRequired());
        dto.setDeadline(project.getDeadline());
        dto.setBudget(project.getBudget());
        dto.setNbPropositions(project.getPropositions().size()); // Set the number of propositions
        dto.setUser(project.getUser()); // Ensure user is set

        return dto;
    }
}
