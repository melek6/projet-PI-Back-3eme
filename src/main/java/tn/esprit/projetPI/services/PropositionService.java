package tn.esprit.projetPI.services;


import org.springframework.web.multipart.MultipartFile;
import tn.esprit.projetPI.controllers.ResourceNotFoundException;
import tn.esprit.projetPI.dto.PropositionDTO;
import tn.esprit.projetPI.dto.DtoMapper;
import tn.esprit.projetPI.models.Proposition;
import tn.esprit.projetPI.models.Project;
import tn.esprit.projetPI.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.projetPI.repository.PropositionRepository;
import tn.esprit.projetPI.repository.UserRepository;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PropositionService implements IPropositionService {

    @Autowired
    private PropositionRepository propositionRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FirebaseStorageService firebaseStorageService;

    @Override
    public List<PropositionDTO> retrieveAllPropositions() {
        List<Proposition> propositions = propositionRepository.findAll();
        return propositions.stream()
                .map(DtoMapper::toPropositionDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PropositionDTO> getPropositionsByProjectId(Long projectId) {
        List<Proposition> propositions = propositionRepository.findByProjectId(projectId);
        return propositions.stream()
                .map(DtoMapper::toPropositionDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Proposition addProposition(Proposition proposition) {
        Proposition savedProposition = propositionRepository.save(proposition);

        // Send notification
        notificationService.sendNewPropositionNotification(proposition.getProject().getUser(), proposition.getProject().getTitle());

        return savedProposition;
    }

    @Override
    public Proposition updateProposition(Long id, Proposition propositionDetails) {
        Proposition existingProposition = propositionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proposition not found"));

        existingProposition.setDetail(propositionDetails.getDetail());
        existingProposition.setAmount(propositionDetails.getAmount());
        existingProposition.setStatus(propositionDetails.getStatus());

        return propositionRepository.save(existingProposition);
    }

    @Override
    public void deleteProposition(Long id) {
        propositionRepository.deleteById(id);
    }

    @Override
    public Proposition approveProposition(Long id, String username) {
        Proposition proposition = propositionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proposition not found"));
        proposition.setStatus("APPROVED");
        Proposition approvedProposition = propositionRepository.save(proposition);

        // Send email notification
        emailService.sendPropositionStatusEmail(proposition.getUser().getEmail(), "APPROVED");

        return approvedProposition;
    }

    @Override
    public Proposition declineProposition(Long id) {
        Proposition proposition = propositionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proposition not found"));
        proposition.setStatus("DECLINED");
        Proposition declinedProposition = propositionRepository.save(proposition);

        // Send email notification
        emailService.sendPropositionStatusEmail(proposition.getUser().getEmail(), "DECLINED");

        return declinedProposition;
    }

    @Override
    public List<PropositionDTO.UserDTO> getUsersWithApprovedPropositions() {
        List<Proposition> approvedPropositions = propositionRepository.findByStatus("APPROVED");
        List<PropositionDTO.UserDTO> users = new ArrayList<>();

        for (Proposition proposition : approvedPropositions) {
            User user = proposition.getUser();
            PropositionDTO.UserDTO userDTO = new PropositionDTO.UserDTO(user.getId(), user.getUsername(), user.getEmail(), proposition.getProject().getId());
            users.add(userDTO);
        }

        return users;
    }

    @Override
    public List<PropositionDTO.UserDTO> getUsersWithApprovedPropositionsForProjectOwner(String ownerUsername) {
        List<Proposition> approvedPropositions = propositionRepository.findByStatus("APPROVED");
        List<PropositionDTO.UserDTO> users = new ArrayList<>();

        for (Proposition proposition : approvedPropositions) {
            Project project = proposition.getProject();
            User owner = project.getUser();

            if (owner.getUsername().equals(ownerUsername)) {
                User user = proposition.getUser();
                PropositionDTO.UserDTO userDTO = new PropositionDTO.UserDTO(user.getId(), user.getUsername(), user.getEmail(), project.getId());
                users.add(userDTO);
            }
        }

        return users;
    }

    @Override
    public List<PropositionDTO> getPropositionsByUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
        List<Proposition> propositions = propositionRepository.findByUserId(user.getId());
        return propositions.stream()
                .map(DtoMapper::toPropositionDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUserProposition(Long id, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
        Proposition proposition = propositionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Proposition not found with id: " + id));
        if (!proposition.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You are not authorized to delete this proposition");
        }
        propositionRepository.delete(proposition);
    }

    @Override
    public Proposition updateUserProposition(Long id, String username, String detail, double amount, MultipartFile file, boolean removeExistingFile) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
        Proposition proposition = propositionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Proposition not found with id: " + id));
        if (!proposition.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You are not authorized to update this proposition");
        }

        proposition.setDetail(detail);
        proposition.setAmount(amount);

        try {
            if (removeExistingFile && proposition.getFilePath() != null) {
                // Delete the existing file
                firebaseStorageService.deleteFile(proposition.getFilePath());
                proposition.setFilePath(null);
            }

            if (file != null && !file.isEmpty()) {
                String filePath = firebaseStorageService.uploadFile(file);
                proposition.setFilePath(filePath);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to handle file operations", e);
        }

        return propositionRepository.save(proposition);
    }


    @Override
    public String uploadFileToFirebase(MultipartFile file) {
        try {
            return firebaseStorageService.uploadFile(file);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file to Firebase", e);
        }
    }

    @Override
    public Optional<Proposition> getPropositionById(Long id) {
        return propositionRepository.findById(id);
    }
}