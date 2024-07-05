package tn.esprit.projetPI.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tn.esprit.projetPI.models.Formation;
import tn.esprit.projetPI.models.FormationCategory;

import java.util.List;
import java.util.Optional;

@Repository
public interface FormationRepository extends JpaRepository<Formation, Integer> {
    List<Formation> findByBestSeller(boolean bestSeller);

    List<Formation> findByCategory(FormationCategory category);
    @Query("SELECT f FROM Formation f LEFT JOIN f.evaluations e GROUP BY f.id ORDER BY AVG(e.score) DESC")
    List<Formation> findTopFormationsByAverageScore();

    List<Formation> findCompletedFormationsByUserId(Long userId);
}
