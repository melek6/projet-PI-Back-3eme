package tn.esprit.projetPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.projetPI.models.Tentative;

import java.util.List;

@Repository
public interface TentativeRepository extends JpaRepository<Tentative, Integer> {
    @Query("SELECT t FROM Tentative t WHERE t.userId = :userId AND t.quizId = :quizId")
    List<Tentative> findTentativeByUserIdAndQuizId(@Param("userId") Long userId, @Param("quizId") Long quizId);

}
