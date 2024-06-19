package tn.esprit.projetPI.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import tn.esprit.projetPI.models.Proposition;
import tn.esprit.projetPI.models.Project;
import tn.esprit.projetPI.models.User;
import tn.esprit.projetPI.repository.ProjectRepository;
import tn.esprit.projetPI.repository.UserRepository;
import tn.esprit.projetPI.services.IPropositionService;

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

    @GetMapping
    public List<Proposition> getAllPropositions() {
        return propositionService.retrieveAllPropositions();
    }

    @GetMapping("/{projectId}")
    public List<Proposition> getPropositionsByProjectId(@PathVariable Long projectId) {
        return propositionService.getPropositionsByProjectId(projectId);
    }

    @PostMapping("/{projectId}")
    public Proposition createProposition(@PathVariable Long projectId, @RequestBody Proposition proposition) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new RuntimeException("Project not found"));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

        proposition.setProject(project);
        proposition.setUser(user);
        proposition.setStatus("PENDING"); // Default status
        return propositionService.addProposition(proposition);
    }

    @PutMapping("/{id}")
    public Proposition updateProposition(@PathVariable Long id, @RequestBody Proposition propositionDetails) {
        return propositionService.updateProposition(id, propositionDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteProposition(@PathVariable Long id) {
        propositionService.deleteProposition(id);
    }

    @PostMapping("/{id}/approve")
    public Proposition approveProposition(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return propositionService.approveProposition(id, username);
    }

    @PostMapping("/{id}/decline")
    public Proposition declineProposition(@PathVariable Long id) {
        return propositionService.declineProposition(id);
    }
}
