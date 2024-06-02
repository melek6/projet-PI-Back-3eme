package tn.esprit.projetPI.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.projetPI.models.Quiz;
import tn.esprit.projetPI.repository.QuizRepository;

import java.util.List;
import java.util.Optional;

@Service
public class QuizImpl {
    @Autowired
    private QuizRepository quizRepository;

    public Quiz saveOrUpdateQuiz(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    public Optional<Quiz> getQuizById(Long id) {
        return quizRepository.findById(id);
    }

    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    public void deleteQuizById(Long id) {
        quizRepository.deleteById(id);
    }
}
