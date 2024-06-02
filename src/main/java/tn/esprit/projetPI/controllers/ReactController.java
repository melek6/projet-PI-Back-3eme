package tn.esprit.projetPI.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.projetPI.models.React;
import tn.esprit.projetPI.repository.ReactRepository;
import tn.esprit.projetPI.services.IReactService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reacts")
public class ReactController {

    @Autowired
    private ReactRepository reactRepository;

    @GetMapping
    public List<React> getAllReacts() {
        return reactRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<React> getReactById(@PathVariable int id) {
        Optional<React> react = reactRepository.findById(id);
        return react.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public React createReact(@RequestBody React react) {
        return reactRepository.save(react);
    }

    @PutMapping("/{id}")
    public ResponseEntity<React> updateReact(@PathVariable int id, @RequestBody React reactDetails) {
        Optional<React> react = reactRepository.findById(id);
        if (react.isPresent()) {
            React updatedReact = react.get();
            updatedReact.setType(reactDetails.getType());
            return ResponseEntity.ok(reactRepository.save(updatedReact));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReact(@PathVariable int id) {
        if (reactRepository.existsById(id)) {
            reactRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
