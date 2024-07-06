package tn.esprit.projetPI.controllers;

import tn.esprit.projetPI.dto.PropositionDTO;
import tn.esprit.projetPI.models.Project;
import tn.esprit.projetPI.models.Proposition;
import tn.esprit.projetPI.models.User;
import tn.esprit.projetPI.repository.ProjectRepository;
import tn.esprit.projetPI.repository.UserRepository;
import tn.esprit.projetPI.services.IPropositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/propositions")
@CrossOrigin(origins = "http://localhost:4200")
public class PropositionController {

    @Autowired
    private IPropositionService propositionService;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    private static final String UPLOADED_FOLDER = "C:/Users/Iyed/Documents/GitHub/projet-PI-Back-3eme/uploads/";

    @GetMapping
    public List<PropositionDTO> getAllPropositions() {
        return propositionService.retrieveAllPropositions();
    }

    @GetMapping("/{projectId}")
    public List<PropositionDTO> getPropositionsByProjectId(@PathVariable Long projectId) {
        return propositionService.getPropositionsByProjectId(projectId);
    }

    @PostMapping(value = "/{projectId}", consumes = {"multipart/form-data"})
    public ResponseEntity<Proposition> createProposition(
            @PathVariable Long projectId,
            @RequestParam("detail") String detail,
            @RequestParam("amount") double amount,
            @RequestParam("file") MultipartFile file) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + projectId));
        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + SecurityContextHolder.getContext().getAuthentication().getName()));

        String filePath = saveUploadedFile(file);

        Proposition proposition = new Proposition(detail, amount, "PENDING", project, user, filePath);
        Proposition savedProposition = propositionService.addProposition(proposition);
        return ResponseEntity.ok(savedProposition);
    }

    private String saveUploadedFile(MultipartFile file) {
        if (file.isEmpty()) {
            return null;
        }

        try {
            // Ensure the directory exists
            Path directory = Paths.get(UPLOADED_FOLDER);
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
            }

            byte[] bytes = file.getBytes();
            Path path = directory.resolve(file.getOriginalFilename());
            Files.write(path, bytes);
            return path.toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }

    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Proposition> updateProposition(@PathVariable Long id, @RequestBody Proposition propositionDetails) {
        Proposition updatedProposition = propositionService.updateProposition(id, propositionDetails);
        return ResponseEntity.ok(updatedProposition);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProposition(@PathVariable Long id) {
        propositionService.deleteProposition(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<Proposition> approveProposition(@PathVariable Long id) {
        Proposition approvedProposition = propositionService.approveProposition(id, SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.ok(approvedProposition);
    }

    @PostMapping("/{id}/decline")
    public ResponseEntity<Proposition> declineProposition(@PathVariable Long id) {
        Proposition declinedProposition = propositionService.declineProposition(id);
        return ResponseEntity.ok(declinedProposition);
    }

    @GetMapping("/users-with-approved-propositions-for-owner")
    public List<PropositionDTO.UserDTO> getUsersWithApprovedPropositionsForProjectOwner() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return propositionService.getUsersWithApprovedPropositionsForProjectOwner(username);
    }
}

