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

    @Column(name = "score")
    private float score; // Ajout de la propriété score

    public Evaluation(String trainingTitle, Date date, String location, String trainer, String participant, int trainingScore, String comments, float score) {
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

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    @ManyToOne
    @JoinColumn(name = "formation_id")
    private Formation formation;

    public void setFormation(Formation formation) {
    }
}