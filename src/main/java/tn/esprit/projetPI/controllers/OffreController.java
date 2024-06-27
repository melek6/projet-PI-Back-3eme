package tn.esprit.projetPI.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.projetPI.models.Offre;
import tn.esprit.projetPI.services.OffreService;

import java.util.List;

@RestController
@RequestMapping("/api/offres")
public class OffreController {

    @Autowired
    private OffreService offreService;

    @PostMapping
    public ResponseEntity<Offre> addOffre(@RequestBody Offre offre) {
        Offre newOffre = offreService.saveOffre(offre);
        return new ResponseEntity<>(newOffre, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Offre>> getAllOffres() {
        List<Offre> offres = offreService.getAllOffres();
        return new ResponseEntity<>(offres, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Offre> getOffreById(@PathVariable("id") int id) {
        Offre offre = offreService.getOffreById(id);
        return new ResponseEntity<>(offre, offre != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }
    @GetMapping("getOffreByIduser/{id}")
    public ResponseEntity<List<Offre>> getOffreByIduser(@PathVariable("id") int id) {
        List<Offre> offre = offreService.getOffreByIduser(id);
        return new ResponseEntity<>(offre, offre != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Offre> updateOffre(@PathVariable("id") int id, @RequestBody Offre offre) {
        Offre updatedOffre = offreService.updateOffre(id, offre);
        return new ResponseEntity<>(updatedOffre, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOffre(@PathVariable("id") int id) {
        offreService.deleteOffre(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
