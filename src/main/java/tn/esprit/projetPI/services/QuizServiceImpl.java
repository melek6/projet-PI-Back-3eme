package tn.esprit.projetPI.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.projetPI.models.Quiz;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/quizzes")
public class QuizServiceImpl {

        @Autowired
        private QuizImpl quizService;

        @PostMapping
        public ResponseEntity<Quiz> createOrUpdateQuiz(@RequestBody Quiz quiz) {
            Quiz savedQuiz = quizService.saveOrUpdateQuiz(quiz);
            return new ResponseEntity<>(savedQuiz, HttpStatus.CREATED);
        }

        @GetMapping("/{id}")
        public ResponseEntity<Quiz> getQuizById(@PathVariable Long id) {
            Optional<Quiz> quiz = quizService.getQuizById(id);
            return quiz.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }

        @GetMapping
        public ResponseEntity<List<Quiz>> getAllQuizzes() {
            List<Quiz> quizzes = quizService.getAllQuizzes();
            return new ResponseEntity<>(quizzes, HttpStatus.OK);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteQuizById(@PathVariable Long id) {
            quizService.deleteQuizById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }


