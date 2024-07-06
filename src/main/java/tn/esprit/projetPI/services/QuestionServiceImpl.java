package tn.esprit.projetPI.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.projetPI.models.Question;
import tn.esprit.projetPI.models.Quiz;

import java.util.List;
import java.util.Optional;

// QuestionController.java
@RestController
@RequestMapping("/api/questions")
@CrossOrigin(origins = "http://localhost:4200") // Allow CORS for Angular frontend
public class QuestionServiceImpl {
    @Autowired
    private QuestionImpl questionService;

    @PostMapping
    public ResponseEntity<Question> createOrUpdateQuestion(@RequestBody Question question, @RequestParam Long quizId) {
        //try {

            Question savedQuestion = questionService.saveOrUpdateQuestion(question, quizId);


            return new ResponseEntity<>(savedQuestion, HttpStatus.CREATED);
//        } catch (RuntimeException e) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable Long id) {
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
    public ResponseEntity<Void> deleteQuestionById(@PathVariable Long id) {
        questionService.deleteQuestionById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Question> updateQuestion(@PathVariable Long id, @RequestBody Question questionDetails) {
        Optional<Question> updatedQuestion = questionService.updateQuestion(id, questionDetails);
        return updatedQuestion.map(question -> new ResponseEntity<>(question, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

 /*   @GetMapping("/{id}/responses")
    public List<String> getReponsesByQuestionId(@PathVariable Long id) {
        return questionService.getReponseContentByQuestionId(id);
    }*/
}
