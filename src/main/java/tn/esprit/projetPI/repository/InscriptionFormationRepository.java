package tn.esprit.projetPI.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.projetPI.models.InscriptionFormation;

import java.util.Optional;

@Repository
public interface InscriptionFormationRepository extends JpaRepository<InscriptionFormation, Integer> {
}