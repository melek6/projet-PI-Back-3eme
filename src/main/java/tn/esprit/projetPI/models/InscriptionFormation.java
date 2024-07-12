package tn.esprit.projetPI.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "inscription_formations")
public class InscriptionFormation implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "registration_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date registrationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "etat", nullable = false)
    private Etat etat;

    @ManyToOne
    @JoinColumn(name = "formation_id", nullable = false)
    private Formation formation;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public InscriptionFormation() {
        this.registrationDate = new Date();
        this.etat = Etat.PENDING;
    }

    public InscriptionFormation(Date registrationDate, Etat etat) {
        this.registrationDate = registrationDate != null ? registrationDate : new Date();
        this.etat = etat != null ? etat : Etat.PENDING;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Etat getEtat() {
        return etat;
    }

    public void setEtat(Etat etat) {
        this.etat = etat;
    }

    public Formation getFormation() {
        return formation;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
