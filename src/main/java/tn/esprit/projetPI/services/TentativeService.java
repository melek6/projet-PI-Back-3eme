package tn.esprit.projetPI.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.projetPI.models.Quiz;
import tn.esprit.projetPI.models.Tentative;
import tn.esprit.projetPI.models.User;
import tn.esprit.projetPI.repository.TentativeRepository;

import java.util.Date;
import java.util.List;

@Service
public class TentativeService {


    @Autowired
        private TentativeRepository tentativeRepository;

        public List<Tentative> hasUserAttemptedQuiz(Long userId, Long quizId) {
            return tentativeRepository.findTentativeByUserIdAndQuizId(userId, quizId);
        }

        public void recordQuizAttempt(Long userId, Long quizId) {
            Tentative attempt = new Tentative();
         //   attempt.setUser(new User(userId)); // Assurez-vous que l'entité User a un constructeur avec userId
         //   attempt.setQuiz(new Quiz(quizId)); // Assurez-vous que l'entité Quiz a un constructeur avec quizId
            attempt.setAttemptDate(new Date());
            tentativeRepository.save(attempt);
        }
    }

