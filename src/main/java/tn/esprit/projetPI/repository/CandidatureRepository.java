package tn.esprit.projetPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.projetPI.models.Candidature;

@Repository
public interface CandidatureRepository extends JpaRepository<Candidature, Integer>{
}
