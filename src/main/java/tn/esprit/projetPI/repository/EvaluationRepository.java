package tn.esprit.projetPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.projetPI.models.Evaluation;

import java.util.List;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Integer> {
    List<Evaluation> findByFormationId(int formationId);

}
