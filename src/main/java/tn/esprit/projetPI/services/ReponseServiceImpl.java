package tn.esprit.projetPI.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.projetPI.models.Reponse;

import java.util.List;
import java.util.Optional;

    @RestController
    @RequestMapping("/api/reponses")
    public class ReponseServiceImpl {
        @Autowired
        private ReponseImpl reponseService;

        @PostMapping
        public ResponseEntity<Reponse> createOrUpdateReponse(@RequestBody Reponse reponse) {
            Reponse savedReponse = reponseService.saveOrUpdateReponse(reponse);
            return new ResponseEntity<>(savedReponse, HttpStatus.CREATED);
        }

        @GetMapping("/{id}")
        public ResponseEntity<Reponse> getReponseById(@PathVariable Integer id) {
            Optional<Reponse> reponse = reponseService.getReponseById(id);
            return reponse.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }

        @GetMapping
        public ResponseEntity<List<Reponse>> getAllReponses() {
            List<Reponse> reponses = reponseService.getAllReponses();
            return new ResponseEntity<>(reponses, HttpStatus.OK);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteReponseById(@PathVariable Integer id) {
            reponseService.deleteReponseById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }


