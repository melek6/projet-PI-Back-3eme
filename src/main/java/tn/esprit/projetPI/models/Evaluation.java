package tn.esprit.projetPI.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "evaluations")
public class Evaluation implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "training_title")
    private String trainingTitle;

    @Column(name = "date")
    private Date date;

    @Column(name = "location")
    private String location;

    @Column(name = "trainer")
    private String trainer;

    @Column(name = "participant")
    private String participant;

    @Column(name = "comments")
    private String comments;

    @Column(nullable = true) // allows null values in the database
    private Integer score; // Use Integer instead of int

    public Evaluation(String trainingTitle, Date date, String location, String trainer, String participant, String comments, Integer score) {
        this.trainingTitle = trainingTitle;
        this.date = date;
        this.location = location;
        this.trainer = trainer;
        this.participant = participant;
        this.comments = comments;
        this.score = score;
    }

    public Evaluation() {
    }

    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTrainingTitle() {
        return trainingTitle;
    }

    public void setTrainingTitle(String trainingTitle) {
        this.trainingTitle = trainingTitle;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTrainer() {
        return trainer;
    }

    public void setTrainer(String trainer) {
        this.trainer = trainer;
    }

    public String getParticipant() {
        return participant;
    }

    public void setParticipant(String participant) {
        this.participant = participant;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Integer getScore() { // Change return type to Integer
        return score;
    }

    public void setScore(Integer score) { // Change parameter type to Integer
        this.score = score;
    }

    @ManyToOne
    @JoinColumn(name = "formation_id")
    private Formation formation;

    public void setFormation(Formation formation) {
        this.formation = formation;
    }
}
