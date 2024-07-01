package tn.esprit.projetPI.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.projetPI.models.Formation;
import tn.esprit.projetPI.models.InscriptionFormation;
import tn.esprit.projetPI.repository.FormationRepository;
import tn.esprit.projetPI.services.InscriptionFormationService;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/inscriptions")
public class InscriptionFormationController {

    private final InscriptionFormationService inscriptionFormationService;
    @Autowired
      private  FormationRepository formationRepository;
    @Autowired
    public InscriptionFormationController(InscriptionFormationService inscriptionFormationService) {
        this.inscriptionFormationService = inscriptionFormationService;
    }






    @PostMapping("/hello")
    public ResponseEntity<InscriptionFormation> createinscfor(@RequestBody Map<String, Object> payload) {
        try {
            String status = (String) payload.get("status");
            Integer formationid = (Integer) payload.get("formationId");

            if (formationid == null) {
                // Handle case where formation_id is not provided
                System.out.println("Formation ID is null.");
                return ResponseEntity.badRequest().body(null);
            }

            System.out.println("Formation ID received: " + formationid);

            Optional<Formation> optionalFormation = formationRepository.findById(formationid);
            if (optionalFormation.isPresent()) {
                Formation formation = optionalFormation.get();
                InscriptionFormation inscriptionFormation = new InscriptionFormation(new Date(), status);
                inscriptionFormation.setFormation(formation);
                InscriptionFormation saveinsfor = inscriptionFormationService.addInscription(inscriptionFormation);
                System.out.println("InscriptionFormation added successfully.");
                return new ResponseEntity<>(saveinsfor, HttpStatus.CREATED);
            } else {
                System.out.println("Formation not found with ID: " + formationid);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
            // Handle exception (e.g., logging, return appropriate error response)
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
}