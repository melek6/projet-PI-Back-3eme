package tn.esprit.projetPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.projetPI.models.Quiz;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
}
