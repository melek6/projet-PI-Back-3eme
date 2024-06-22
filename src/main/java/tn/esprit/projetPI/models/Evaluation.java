package tn.esprit.projetPI.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "evaluations")
public class Evaluation implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "comments")
    private String comments;

    @Column(nullable = true) // allows null values in the database
    private Integer score; // Use Integer instead of int

    // Default constructor
    public Evaluation() {}

    // Parameterized constructor
    public Evaluation(String comments, Integer score) {
        this.comments = comments;
        this.score = score;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @ManyToOne
    @JoinColumn(name = "formation_id")
    private Formation formation;

    public Formation getFormation() {
        return formation;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }
}
