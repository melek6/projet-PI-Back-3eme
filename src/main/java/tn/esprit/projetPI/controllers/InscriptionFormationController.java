package tn.esprit.projetPI.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import tn.esprit.projetPI.models.Etat;
import tn.esprit.projetPI.models.Formation;
import tn.esprit.projetPI.models.InscriptionFormation;
import tn.esprit.projetPI.models.User;
import tn.esprit.projetPI.repository.FormationRepository;
import tn.esprit.projetPI.repository.UserRepository;
import tn.esprit.projetPI.services.EmailService;
import tn.esprit.projetPI.services.InscriptionFormationService;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/inscriptions")
public class InscriptionFormationController {

    private final InscriptionFormationService inscriptionFormationService;
    private final EmailService emailService;
    private final UserRepository userRepository;
    private final FormationRepository formationRepository;

    @Autowired
    public InscriptionFormationController(InscriptionFormationService inscriptionFormationService, EmailService emailService, UserRepository userRepository, FormationRepository formationRepository) {
        this.inscriptionFormationService = inscriptionFormationService;
        this.emailService = emailService;
        this.userRepository = userRepository;
        this.formationRepository = formationRepository;
    }

    @PostMapping("/hello")
    public ResponseEntity<InscriptionFormation> createinscfor(@RequestBody Map<String, Object> payload) {
        try {
            String etatStr = (String) payload.get("etat");
            Integer formationId = (Integer) payload.get("formationId");

            if (formationId == null) {
                System.out.println("Formation ID is null.");
                return ResponseEntity.badRequest().body(null);
            }

            System.out.println("Formation ID received: " + formationId);

            Optional<Formation> optionalFormation = formationRepository.findById(formationId);
            if (optionalFormation.isPresent()) {
                Formation formation = optionalFormation.get();
                Etat etat = Etat.valueOf(etatStr.toUpperCase());  // Convert the string to the enum type
                InscriptionFormation inscriptionFormation = new InscriptionFormation(new Date(), etat);
                inscriptionFormation.setFormation(formation);

                // Get the current user
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                String username = authentication.getName();
                User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
                inscriptionFormation.setUser(user);

                InscriptionFormation saveInscription = inscriptionFormationService.addInscription(inscriptionFormation);
                System.out.println("InscriptionFormation added successfully.");

                // Send email notification to the current user
                String subject = "New Inscription Created";
                String text = "Dear " + user.getUsername() + ",\n\nYour inscription with status: " + etat + " for formation: " + formation.getTitle() + " has been created successfully.";
                emailService.sendSimpleEmail(user.getEmail(), subject, text);

                return new ResponseEntity<>(saveInscription, HttpStatus.CREATED);
            } else {
                System.out.println("Formation not found with ID: " + formationId);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public List<InscriptionFormation> getAllInscriptions() {
        return inscriptionFormationService.retrieveAllInscriptions();
    }

    @GetMapping("/{id}")
    public InscriptionFormation getInscriptionById(@PathVariable int id) {
        return inscriptionFormationService.retrieveInscription(id).orElse(null);
    }

    @PostMapping
    public InscriptionFormation createInscription(@RequestBody InscriptionFormation inscriptionFormation) {
        return inscriptionFormationService.addInscription(inscriptionFormation);
    }

    @PutMapping("/{id}")
    public InscriptionFormation updateInscription(@PathVariable int id, @RequestBody InscriptionFormation inscriptionFormation) {
        inscriptionFormation.setId(id);
        return inscriptionFormationService.updateInscription(inscriptionFormation);
    }

    @DeleteMapping("/{id}")
    public void deleteInscription(@PathVariable int id) {
        inscriptionFormationService.deleteInscription(id);
    }

    @GetMapping("/user/{userId}/completed")
    public List<InscriptionFormation> getCompletedFormationsByUser(@PathVariable Long userId) {
        return inscriptionFormationService.getCompletedFormationsByUser(userId);
    }
    @GetMapping("/user/{userId}/etat/{etat}")
    public List<InscriptionFormation> getInscriptionsByUserAndStatus(@PathVariable Long userId, @PathVariable Etat etat) {
        return inscriptionFormationService.getInscriptionsByUserAndStatus(userId, etat);
    }

}
