package tn.esprit.projetPI.models;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Candidature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Temporal(TemporalType.DATE)
    private Date date;

    private String nom;
    private String prenom;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] cv;

    @ManyToOne
    @JoinColumn(name = "offre_id") // Nom de la colonne dans la table de candidature faisant référence à l'offre
    private Offre offre;

    // Constructors
    public Candidature() {
    }

    public Candidature(Date date, String nom, String prenom, byte[] cv) {
        this.date = date;
        this.nom = nom;
        this.prenom = prenom;
        this.cv = cv;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public byte[] getCv() {
        return cv;
    }

    public void setCv(byte[] cv) {
        this.cv = cv;
    }

    public Offre getOffre() {
        return offre;
    }

    public void setOffre(Offre offre) {
        this.offre = offre;
    }

    // toString method
    @Override
    public String toString() {
        return "Candidature{" +
                "id=" + id +
                ", date=" + date +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                '}';
    }
}