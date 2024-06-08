package tn.esprit.projetPI.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.projetPI.models.Question;
import tn.esprit.projetPI.models.Quiz;
import tn.esprit.projetPI.repository.QuestionRepository;
import tn.esprit.projetPI.repository.QuizRepository;

import java.util.List;
import java.util.Optional;

@Service
public class QuizImpl {
    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private QuestionRepository questionRepository;
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

    public Quiz updateQuiz(Long id, Quiz updatedQuiz) {
        Optional<Quiz> existingQuizOpt = quizRepository.findById(id);
        if (existingQuizOpt.isPresent()) {
            Quiz existingQuiz = existingQuizOpt.get();
            // Update the fields of the existing quiz with the new values
            existingQuiz.setTitle(updatedQuiz.getTitle());
            existingQuiz.setDescription(updatedQuiz.getDescription());
            existingQuiz.setQuestions(updatedQuiz.getQuestions());
            // Save the updated quiz
            return quizRepository.save(existingQuiz);
        } else {
            throw new RuntimeException("Quiz with id " + id + " not found");
        }
    }
    public List<Question> getQuestionsByQuizId(Long quizId) {
        Optional<Quiz> quizOptional = quizRepository.findById(quizId);
        if (quizOptional.isPresent()) {
            Quiz quiz = quizOptional.get();
            return quiz.getQuestions();
        } else {
            throw new RuntimeException("Quiz with id " + quizId + " not found");
        }
    }
}
