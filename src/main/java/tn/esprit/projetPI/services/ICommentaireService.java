package tn.esprit.projetPI.services;

import tn.esprit.projetPI.models.Commentaire;

import java.util.List;
import java.util.Optional;

public interface ICommentaireService {
    List<Commentaire> getAllCommentaires();
    Optional<Commentaire> getCommentaireById(int id);
    Commentaire createCommentaire(Commentaire commentaire);
    Commentaire updateCommentaire(int id, Commentaire commentaireDetails);
    void deleteCommentaire(int id);
}
