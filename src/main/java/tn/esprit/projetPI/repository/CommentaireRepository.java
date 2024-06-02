package tn.esprit.projetPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.projetPI.models.Commentaire;

@Repository
public interface CommentaireRepository extends JpaRepository<Commentaire, Integer> {}