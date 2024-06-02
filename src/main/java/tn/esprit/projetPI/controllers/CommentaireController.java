package tn.esprit.projetPI.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.projetPI.models.Commentaire;
import tn.esprit.projetPI.repository.CommentaireRepository;
import tn.esprit.projetPI.services.ICommentaireService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/commentaires")
public class CommentaireController {

    @Autowired
    private CommentaireRepository commentaireRepository;

    @GetMapping
    public List<Commentaire> getAllCommentaires() {
        return commentaireRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Commentaire> getCommentaireById(@PathVariable int id) {
        Optional<Commentaire> commentaire = commentaireRepository.findById(id);
        return commentaire.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Commentaire createCommentaire(@RequestBody Commentaire commentaire) {
        return commentaireRepository.save(commentaire);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Commentaire> updateCommentaire(@PathVariable int id, @RequestBody Commentaire commentaireDetails) {
        Optional<Commentaire> commentaire = commentaireRepository.findById(id);
        if (commentaire.isPresent()) {
            Commentaire updatedCommentaire = commentaire.get();
            updatedCommentaire.setContent(commentaireDetails.getContent());
            updatedCommentaire.setDate(commentaireDetails.getDate());
            return ResponseEntity.ok(commentaireRepository.save(updatedCommentaire));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommentaire(@PathVariable int id) {
        if (commentaireRepository.existsById(id)) {
            commentaireRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
