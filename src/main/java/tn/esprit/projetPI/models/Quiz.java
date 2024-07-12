package tn.esprit.projetPI.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Unique identifier for the Quiz

    private String title; // Title of the Quiz
    private String description; // Description of the Quiz

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    private List<Question> questions; // List of questions associated with the Quiz

   // @OneToOne(mappedBy = "quiz")
   // private Tentative tentative; // Tentative associated with the Quiz

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // User who created the Quiz

    public Quiz(Long quizId) {
    }

    public Quiz() {

    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

   /* public Tentative getTentative() {
        return tentative;
    }

    public void setTentative(Tentative tentative) {
        this.tentative = tentative;
    } */

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
