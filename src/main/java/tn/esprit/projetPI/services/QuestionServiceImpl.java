package tn.esprit.projetPI.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.projetPI.models.Question;

import java.util.List;
import java.util.Optional;

    @RestController
    @RequestMapping("/api/questions")
    public class QuestionServiceImpl {
        @Autowired
        private QuestionImpl questionService;

        @PostMapping
        public ResponseEntity<Question> createOrUpdateQuestion(@RequestBody Question question) {
            Question savedQuestion = questionService.saveOrUpdateQuestion(question);
            return new ResponseEntity<>(savedQuestion, HttpStatus.CREATED);
        }

        @GetMapping("/{id}")
        public ResponseEntity<Question> getQuestionById(@PathVariable Integer id) {
            Optional<Question> question = questionService.getQuestionById(id);
            return question.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }

        @GetMapping
        public ResponseEntity<List<Question>> getAllQuestions() {
            List<Question> questions = questionService.getAllQuestions();
            return new ResponseEntity<>(questions, HttpStatus.OK);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteQuestionById(@PathVariable Integer id) {
            questionService.deleteQuestionById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }


