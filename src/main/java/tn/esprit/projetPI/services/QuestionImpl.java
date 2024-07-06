package tn.esprit.projetPI.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.projetPI.models.Question;
import tn.esprit.projetPI.models.Quiz;
import tn.esprit.projetPI.models.Reponse;
import tn.esprit.projetPI.repository.QuestionRepository;
import tn.esprit.projetPI.repository.QuizRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuestionImpl {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuizRepository quizRepository;

    public Question saveOrUpdateQuestion(Question question, Long quizId) {
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> new RuntimeException("Quiz not found"));
        question.setQuiz(quiz);
        return questionRepository.save(question);
    }

    public Optional<Question> getQuestionById(Long id) {
        return questionRepository.findById(id);
    }

    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    public void deleteQuestionById(Long id) {
        questionRepository.deleteById(id);
    }

    public Optional<Question> updateQuestion(Long id, Question questionDetails) {
        return questionRepository.findById(id).map(question -> {
            question.setContent(questionDetails.getContent());
            question.setType(questionDetails.getType());
            return questionRepository.save(question);
        });
    }

  /*  public List<String> getReponseContentByQuestionId(Long questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"))
                .getReponses()
                .stream()
                .map(Reponse::getContent)
                .collect(Collectors.toList());
    }*/
}
