package tn.esprit.projetPI.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.projetPI.models.Formation;
import tn.esprit.projetPI.models.Evaluation;
import tn.esprit.projetPI.models.FormationCategory;
import tn.esprit.projetPI.services.FormationService;
import tn.esprit.projetPI.services.EvaluationService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/formations")
public class FormationController {
@Autowired
    private  FormationService formationService;
@Autowired
    private  EvaluationService evaluationService;

    @Value("${file.upload-dir}")
    private String uploadDir;

//    @Autowired
//    public FormationController(FormationService formationService, EvaluationService evaluationService) {
//        this.formationService = formationService;
//        this.evaluationService = evaluationService;
//    }

    @GetMapping
    public List<Formation> getAllFormations() {
        return formationService.retrieveAllFormations();
    }

    @GetMapping("/{id}")
    public Formation getFormationById(@PathVariable int id) {
        return formationService.retrieveFormation(id).orElse(null);
    }
    @GetMapping("/recommended")
    public List<Formation> getRecommendedFormations() {
        return formationService.getRecommendedFormations();  }
    @PostMapping
    public Formation createFormation(@RequestBody Formation formation) {
        return formationService.addFormation(formation);
    }
//    @GetMapping("/completed/{userId}")
//    public List<Formation> getCompletedFormationsByUser(@PathVariable Long userId) {
//        return formationService.getCompletedFormationsByUser(userId);
//    }
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



    @PostMapping("/{formationId}/evaluations")
    public Evaluation addEvaluationToFormation(@PathVariable int formationId, @RequestBody Evaluation evaluation) {
        Formation formation = formationService.retrieveFormation(formationId).orElseThrow(() -> new ResourceNotFoundException("Formation not found with id: " + formationId));
        evaluation.setFormation(formation);
        return evaluationService.addEvaluation(evaluation);
    }

    @PostMapping("/category/{category}")
    public Formation createFormationByCategory(@RequestBody Formation formation, @PathVariable FormationCategory category) {
        return formationService.addFormationByCategory(formation, category);
    }

    @GetMapping("/categories")
    public ResponseEntity<FormationCategory[]> getAllCategories() {
        return new ResponseEntity<>(FormationCategory.values(), HttpStatus.OK);
    }

    @GetMapping("/category/{category}")
    public List<Formation> getFormationsByCategory(@PathVariable FormationCategory category) {
        return formationService.getFormationsByCategory(category);
    }
    @GetMapping("/completed/{userId}")
    public List<Formation> getCompletedFormations(@PathVariable Long userId) {
        return formationService.getCompletedFormations(userId);
    }
    @PostMapping("/{formationId}/uploadPlanning")
    public ResponseEntity<String> uploadPlanning(@PathVariable int formationId, @RequestParam("file") MultipartFile file) {
        Formation formation = formationService.retrieveFormation(formationId)
                .orElseThrow(() -> new ResourceNotFoundException("Formation not found with id: " + formationId));

        if (file.isEmpty()) {
            return new ResponseEntity<>("Please select a file to upload.", HttpStatus.BAD_REQUEST);
        }

        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(uploadDir + file.getOriginalFilename());
            Files.write(path, bytes);

            formation.setPlanning(file.getOriginalFilename());
            formationService.updateFormation(formation);

            return new ResponseEntity<>("You successfully uploaded '" + file.getOriginalFilename() + "'", HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to upload '" + file.getOriginalFilename() + "'", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    }
