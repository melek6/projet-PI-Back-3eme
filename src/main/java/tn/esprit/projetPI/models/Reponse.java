package tn.esprit.projetPI.models;

import javax.persistence.*;

@Entity
public class Reponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String content;
    private Boolean iscorrect;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    // Getters
    public Integer getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Boolean getIscorrect() {
        return iscorrect;
    }

    public Question getQuestion() {
        return question;
    }

    // Setters
    public void setId(Integer id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setIscorrect(Boolean iscorrect) {
        this.iscorrect = iscorrect;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
