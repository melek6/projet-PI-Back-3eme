package tn.esprit.projetPI.models;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Tentative {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Date attemptDate;
    private Float score;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    // Getters
    public Integer getId() {
        return id;
    }

    public Date getAttemptDate() {
        return attemptDate;
    }

    public Float getScore() {
        return score;
    }

    public User getUser() {
        return user;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    // Setters
    public void setId(Integer id) {
        this.id = id;
    }

    public void setAttemptDate(Date attemptDate) {
        this.attemptDate = attemptDate;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }
}
