package tn.esprit.projetPI.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String description;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    private List<Question> questions;

    @OneToOne(mappedBy = "quiz")
    private Tentative tentative;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Getters
    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public Tentative getTentative() {
        return tentative;
    }

    public User getUser() {
        return user;
    }

    // Setters
    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public void setTentative(Tentative tentative) {
        this.tentative = tentative;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
