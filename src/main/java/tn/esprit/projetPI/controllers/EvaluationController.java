package tn.esprit.projetPI.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.projetPI.models.Evaluation;
import tn.esprit.projetPI.services.EvaluationService;
import tn.esprit.projetPI.services.FormationService;

import java.util.List;

@RestController
@RequestMapping("/api/evalformation")
public class EvaluationController {

    private final EvaluationService evaluationService;
    private FormationService formationService;
    @Autowired
    public EvaluationController(EvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }

    @GetMapping
    public List<Evaluation> getAllEvaluations() {
        return evaluationService.getAllEvaluations();
    }

    @GetMapping("/{id}")
    public Evaluation getEvaluationById(@PathVariable int id) {
        return evaluationService.getEvaluationById(id).orElse(null);
    }

    @PostMapping
    public Evaluation addEvaluation(@RequestBody Evaluation evaluation) {
        return evaluationService.addEvaluation(evaluation);
    }

    @PutMapping("/{id}")
    public Evaluation updateEvaluation(@PathVariable int id, @RequestBody Evaluation evaluation) {
        evaluation.setId(id);
        return evaluationService.updateEvaluation(evaluation);
    }

    @DeleteMapping("/{id}")
    public void deleteEvaluation(@PathVariable int id) {
        evaluationService.deleteEvaluation(id);
    }

    @PostMapping("/{formationId}/evaluations")
    public ResponseEntity<Evaluation> addEvaluationToFormation(@PathVariable int formationId, @RequestBody Evaluation evaluation) {
        try {
            Evaluation createdEvaluation = formationService.addEvaluationToFormation(formationId, evaluation);
            return new ResponseEntity<>(createdEvaluation, HttpStatus.CREATED);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
