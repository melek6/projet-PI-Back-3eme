package tn.esprit.projetPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
        import org.springframework.stereotype.Repository;
import tn.esprit.projetPI.models.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {


}
