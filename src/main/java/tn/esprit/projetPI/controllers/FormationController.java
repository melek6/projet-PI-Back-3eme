package tn.esprit.projetPI.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.projetPI.models.Formation;
import tn.esprit.projetPI.models.Evaluation;
import tn.esprit.projetPI.services.FormationService;
import tn.esprit.projetPI.services.EvaluationService;

import java.util.List;

@RestController
@RequestMapping("/api/formations")
public class FormationController {

    private final FormationService formationService;
    private final EvaluationService evaluationService;

    @Autowired
    public FormationController(FormationService formationService, EvaluationService evaluationService) {
        this.formationService = formationService;
        this.evaluationService = evaluationService;
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

    @GetMapping("/bestsellers")
    public List<Formation> getBestSellerFormations() {
        return formationService.getBestSellerFormations();
    }

    @GetMapping("/new")
    public List<Formation> getNewFormations() {
        return formationService.getNewFormations();
    }


    @PostMapping("/{formationId}/evaluations")
    public Evaluation addEvaluationToFormation(@PathVariable int formationId, @RequestBody Evaluation evaluation) {
        Formation formation = formationService.retrieveFormation(formationId).orElseThrow(() -> new ResourceNotFoundException("Formation not found with id: " + formationId));
        evaluation.setFormation(formation);
        return evaluationService.addEvaluation(evaluation);
    }
}
