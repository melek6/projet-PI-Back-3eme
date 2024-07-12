package tn.esprit.projetPI.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.projetPI.dto.PropositionDTO;
import tn.esprit.projetPI.models.Project;
import tn.esprit.projetPI.models.Proposition;
import tn.esprit.projetPI.models.User;
import tn.esprit.projetPI.repository.ProjectRepository;
import tn.esprit.projetPI.repository.UserRepository;
import tn.esprit.projetPI.services.FirebaseStorageService;
import tn.esprit.projetPI.services.IPropositionService;
import tn.esprit.projetPI.services.QRCodeGeneratorService;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;


@RestController
@RequestMapping("/api/propositions")
@CrossOrigin(origins = "http://localhost:4200")
public class PropositionController {

    @Autowired
    private IPropositionService propositionService;

    @Autowired
    private FirebaseStorageService firebaseStorageService;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;



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

        String filePath;
        try {
            filePath = firebaseStorageService.uploadFile(file);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file", e);
        }

        Proposition proposition = new Proposition(detail, amount, "PENDING", project, user, filePath);
        Proposition savedProposition = propositionService.addProposition(proposition);
        return ResponseEntity.ok(savedProposition);
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

    @GetMapping("/user")
    public List<PropositionDTO> getUserPropositions() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return propositionService.getPropositionsByUser(username);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> deleteUserProposition(@PathVariable Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        propositionService.deleteUserProposition(id, username);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<Proposition> updateUserProposition(
            @PathVariable Long id,
            @RequestParam("detail") String detail,
            @RequestParam("amount") double amount,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "removeExistingFile", required = false, defaultValue = "false") boolean removeExistingFile) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Proposition updatedProposition = propositionService.updateUserProposition(id, username, detail, amount, file, removeExistingFile);
        return ResponseEntity.ok(updatedProposition);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id) {
        Proposition proposition = propositionService.getPropositionById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Proposition not found with id: " + id));

        String filePath = proposition.getFilePath();
        if (filePath == null || filePath.isEmpty()) {
            throw new RuntimeException("File path is not available for this proposition");
        }

        ByteArrayResource resource;
        try {
            resource = firebaseStorageService.downloadFile(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Error while downloading file", e);
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + Paths.get(filePath).getFileName().toString() + "\"")
                .body(resource);
    }


    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(@RequestParam("filePath") String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            throw new RuntimeException("File path is not available for this proposition");
        }

        ByteArrayResource resource;
        try {
            resource = firebaseStorageService.downloadFile(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Error while downloading file", e);
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filePath + "\"")
                .body(resource);
    }


    @GetMapping("/approved")
    public List<PropositionDTO> getApprovedPropositions() {
        return propositionService.getApprovedPropositions();
    }


}
