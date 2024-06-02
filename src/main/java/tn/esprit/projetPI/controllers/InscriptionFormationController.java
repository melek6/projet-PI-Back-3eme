package tn.esprit.projetPI.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.projetPI.models.InscriptionFormation;
import tn.esprit.projetPI.services.InscriptionFormationService;

import java.util.List;

@RestController
@RequestMapping("/api/inscriptions")
public class InscriptionFormationController {

    private final InscriptionFormationService inscriptionFormationService;

    @Autowired
    public InscriptionFormationController(InscriptionFormationService inscriptionFormationService) {
        this.inscriptionFormationService = inscriptionFormationService;
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