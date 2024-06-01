package tn.esprit.projetPI.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.projetPI.models.Candidature;
import tn.esprit.projetPI.services.CandidatureService;

import java.util.List;

@RestController
@RequestMapping("/api/candidatures")
public class CandidatureController {

    @Autowired
    private CandidatureService candidatureService;

    @PostMapping
    public ResponseEntity<Candidature> addCandidature(@RequestBody Candidature candidature) {
        Candidature newCandidature = candidatureService.saveCandidature(candidature);
        return new ResponseEntity<>(newCandidature, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Candidature>> getAllCandidatures() {
        List<Candidature> candidatures = candidatureService.getAllCandidatures();
        return new ResponseEntity<>(candidatures, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Candidature> getCandidatureById(@PathVariable("id") int id) {
        Candidature candidature = candidatureService.getCandidatureById(id);
        return new ResponseEntity<>(candidature, candidature != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Candidature> updateCandidature(@PathVariable("id") int id, @RequestBody Candidature candidature) {
        Candidature updatedCandidature = candidatureService.updateCandidature(candidature);
        return new ResponseEntity<>(updatedCandidature, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCandidature(@PathVariable("id") int id) {
        candidatureService.deleteCandidature(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
