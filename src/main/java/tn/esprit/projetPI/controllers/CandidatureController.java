package tn.esprit.projetPI.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.projetPI.models.Candidature;
import tn.esprit.projetPI.models.Offre;
import tn.esprit.projetPI.models.User;
import tn.esprit.projetPI.repository.OffreRepository;
import tn.esprit.projetPI.repository.UserRepository;
import tn.esprit.projetPI.services.CandidatureService;
import tn.esprit.projetPI.services.EmailService;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/candidatures")
public class CandidatureController {

    @Autowired
    private CandidatureService candidatureService;

    @Autowired
    private OffreRepository offreRepository;
    @Autowired
    private EmailService emailService;



    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Candidature> createCandidature(
            @RequestParam("nom") String nom,
            @RequestParam("mail") String mail,
            @RequestParam("cv") MultipartFile cv,
            @RequestParam(value = "offre_id", required = false) Integer offreId) {

        try {
            if (offreId == null) {
                // Handle case where offreId is not provided
                return ResponseEntity.badRequest().body(null);
            }

            Optional<Offre> optionalOffre = offreRepository.findById(offreId);
            if (optionalOffre.isPresent()) {
                Offre offre = optionalOffre.get();
                Candidature candidature = new Candidature(new Date(), nom, mail, cv.getBytes());
                candidature.setOffre(offre);
                Candidature savedCandidature = candidatureService.saveCandidature(candidature);
                emailService.sendSimpleEmail(mail,"Votre candidature pour le poste de " + candidature.getOffre().getTitle() + " a été enregistrée avec succès.","Votre candidature a été enregistrée avec succès");
                return ResponseEntity.ok(savedCandidature);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

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