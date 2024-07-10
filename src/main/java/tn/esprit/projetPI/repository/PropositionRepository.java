package tn.esprit.projetPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.projetPI.models.Proposition;

import java.util.List;

@Repository
public interface PropositionRepository extends JpaRepository<Proposition, Long> {
    List<Proposition> findByProjectId(Long projectId);

    List<Proposition> findByStatus(String status);

    List<Proposition> findByUserId(Long userId);
}
