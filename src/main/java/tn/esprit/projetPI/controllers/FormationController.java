package tn.esprit.projetPI.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.projetPI.models.Formation;
import tn.esprit.projetPI.services.FormationService;

import java.util.List;

@RestController
@RequestMapping("/api/formations")
public class FormationController {

    private final FormationService formationService;

    @Autowired
    public FormationController(FormationService formationService) {
        this.formationService = formationService;
    }

    @GetMapping
    public List<Formation> getAllFormations() {
        return formationService.retrieveAllFormations();
    }

    @GetMapping("/{id}")
    public Formation getFormationById(@PathVariable int id) {
        return formationService.retrieveFormation(id).orElse(null);
    }

    @PostMapping
    public Formation createFormation(@RequestBody Formation formation) {
        return formationService.addFormation(formation);
    }

    @PutMapping("/{id}")
    public Formation updateFormation(@PathVariable int id, @RequestBody Formation formation) {
        formation.setId(id);
        return formationService.updateFormation(formation);
    }

    @DeleteMapping("/{id}")
    public void deleteFormation(@PathVariable int id) {
        formationService.deleteFormation(id);
    }
}
