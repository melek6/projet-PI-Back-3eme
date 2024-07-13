package tn.esprit.projetPI.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.projetPI.models.*;
import tn.esprit.projetPI.repository.QuestionRepository;
import tn.esprit.projetPI.repository.QuizRepository;
import tn.esprit.projetPI.repository.TentativeRepository;
import tn.esprit.projetPI.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuestionImpl {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService  emailService;
    @Autowired
    private TentativeRepository tentativeRepository;

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

   public List<String> getReponseContentByQuestionId(Long questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"))
                .getReponses()
                .stream()
                .map(Reponse::getContent)
                .collect(Collectors.toList());
    }

    public  void sendMailValidation(Long idUser, Long score,Long quizId){
        User user = userRepository.findById(idUser).orElse(null);
        Quiz quiz = quizRepository.findById(quizId).orElse(null);
        if(user != null) {
            emailService.sendSimpleEmail(user.getEmail(),
                    "Votre score est:" + score,"" );
            Tentative tentative = new Tentative();
            tentative.setQuizId(quizId);
            tentative.setUserId(idUser);
           tentative.setScore(score);
            tentative.setAttemptDate(LocalDateTime.now());
            tentativeRepository.save(tentative);
        }
    }
}
