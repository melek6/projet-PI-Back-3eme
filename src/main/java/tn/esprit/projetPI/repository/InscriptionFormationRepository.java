package tn.esprit.projetPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.projetPI.models.InscriptionFormation;
import tn.esprit.projetPI.models.Etat;

import java.util.Date;
import java.util.List;

@Repository
public interface InscriptionFormationRepository extends JpaRepository<InscriptionFormation, Integer> {
    List<InscriptionFormation> findByUserIdAndEtat(Long userId, Etat etat);
    List<InscriptionFormation> findByUserIdAndFormationEndDateBefore(Long userId, Date now);
}
