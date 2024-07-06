package tn.esprit.projetPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.projetPI.models.Tentative;

@Repository
public interface TentativeRepository extends JpaRepository<Tentative, Integer> {
    boolean existsByUserIdAndQuizId(Long userId, Long quizId);

}
