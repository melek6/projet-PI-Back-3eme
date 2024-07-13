package tn.esprit.projetPI.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Tentative {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime attemptDate;
    private Long score;
    private Long userId;
    private Long quizId;
    /*
@JsonIgnore
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
@JsonIgnore
    @OneToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;
*/
    // Getters
    public Integer getId() {
        return id;
    }

    public LocalDateTime getAttemptDate() {
        return attemptDate;
    }

    public Long getScore() {
        return score;
    }
    public Long getUserId() {
        return userId;
    }
    public Long getQuizId() {
        return quizId;
    }
/*
    public User getUser() {
        return user;
    }

    public Quiz getQuiz() {
        return quiz;
    }*/

    // Setters
    public void setId(Integer id) {
        this.id = id;
    }

    public void setAttemptDate(LocalDateTime attemptDate) {
        this.attemptDate = attemptDate;
    }

    public void setScore(Long score) {
        this.score = score;
    }
/*
    public void setUser(User user) {
        this.user = user;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }*/
public void setUserId(Long userId) {
    this.userId = userId;
}
    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }
}
