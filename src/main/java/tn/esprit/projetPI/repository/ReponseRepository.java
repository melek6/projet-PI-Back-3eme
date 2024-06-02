package tn.esprit.projetPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.projetPI.models.Reponse;

@Repository

public interface ReponseRepository extends JpaRepository<Reponse, Integer> {
}
