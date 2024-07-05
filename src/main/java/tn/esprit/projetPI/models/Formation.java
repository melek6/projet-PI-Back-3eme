package tn.esprit.projetPI.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "formations")
public class Formation implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private boolean bestSeller;
    private String description;
    private String trainer;
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Temporal(TemporalType.DATE)
    private Date endDate;
    private String location;
    private String planning;

    @Column(name = "price", nullable = false)
    private double price;
    @Column(name = "number_of_hours", nullable = false)
    private int numberOfHours;
    @Enumerated(EnumType.STRING)
    private FormationCategory category;

    @OneToMany(mappedBy = "formation", cascade = CascadeType.ALL)
    private List<Evaluation> evaluations;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")

    private User user;
    @JsonIgnore

    @OneToMany(mappedBy = "formation", cascade = CascadeType.ALL)
    private List<InscriptionFormation> inscriptions;

    public Formation() {
    }

    public Formation(String title, String description,String trainer, String planning, Date startDate, Date endDate, String location, double price, int numberOfHours, FormationCategory category, User user, boolean bestSeller) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.price = price;
        this.numberOfHours = numberOfHours;
        this.category = category;
        this.user = user;
        this.bestSeller = bestSeller;
        this.planning = planning;
        this.trainer = trainer;
    }

    // Getters and Setters

    public String getPlanning() {
        return planning;
    }

    public void setPlanning(String planning) {
        this.planning = planning;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getNumberOfHours() {
        return numberOfHours;
    }

    public void setNumberOfHours(int numberOfHours) {
        this.numberOfHours = numberOfHours;
    }

    public FormationCategory getCategory() {
        return category;
    }

    public void setCategory(FormationCategory category) {
        this.category = category;
    }

    public List<Evaluation> getEvaluations() {
        return evaluations;
    }

    public void setEvaluations(List<Evaluation> evaluations) {
        this.evaluations = evaluations;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public String getTrainer() {
        return trainer;
    }

    public void setTrainer(String trainer) {
        this.trainer = trainer;
    }

    public boolean isBestSeller() {
        return bestSeller;
    }

    public void setBestSeller(boolean bestSeller) {
        this.bestSeller = bestSeller;
    }

    public List<InscriptionFormation> getInscriptions() {
        return inscriptions;
    }

    public void setInscriptions(List<InscriptionFormation> inscriptions) {
        this.inscriptions = inscriptions;
    }
}
