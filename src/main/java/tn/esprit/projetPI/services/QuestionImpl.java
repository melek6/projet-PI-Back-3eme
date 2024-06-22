package tn.esprit.projetPI.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.projetPI.models.Question;
import tn.esprit.projetPI.repository.QuestionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionImpl {
    @Autowired
    private QuestionRepository questionRepository;

    public Question saveOrUpdateQuestion(Question question) {
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
            question.setReponses(questionDetails.getReponses());
            return questionRepository.save(question);
        });
    }
}
