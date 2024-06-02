package tn.esprit.projetPI.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.projetPI.models.Commentaire;
import tn.esprit.projetPI.repository.CommentaireRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CommentaireService implements ICommentaireService {

    @Autowired
    private CommentaireRepository commentaireRepository;

    @Override
    public List<Commentaire> getAllCommentaires() {
        return commentaireRepository.findAll();
    }

    @Override
    public Optional<Commentaire> getCommentaireById(int id) {
        return commentaireRepository.findById(id);
    }

    @Override
    public Commentaire createCommentaire(Commentaire commentaire) {
        return commentaireRepository.save(commentaire);
    }

    @Override
    public Commentaire updateCommentaire(int id, Commentaire commentaireDetails) {
        Optional<Commentaire> commentaire = commentaireRepository.findById(id);
        if (commentaire.isPresent()) {
            Commentaire updatedCommentaire = commentaire.get();
            updatedCommentaire.setContent(commentaireDetails.getContent());
            updatedCommentaire.setDate(commentaireDetails.getDate());
            return commentaireRepository.save(updatedCommentaire);
        } else {
            return null;
        }
    }

    @Override
    public void deleteCommentaire(int id) {
        if (commentaireRepository.existsById(id)) {
            commentaireRepository.deleteById(id);
        }
    }
}
