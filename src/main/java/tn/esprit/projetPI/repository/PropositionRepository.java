package tn.esprit.projetPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.projetPI.models.Proposition;

import java.util.List;

public interface PropositionRepository extends JpaRepository<Proposition, Long> {
    List<Proposition> findByProjectId(Long projectId);
}
