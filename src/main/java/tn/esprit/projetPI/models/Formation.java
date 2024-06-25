package tn.esprit.projetPI.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

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


    private boolean newFormation;

    private String description;

    private Date schedule;
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
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    public Formation() {
    }

    public Formation(String title, String description, Date schedule,String planning,Date startDate, Date endDate, String location, double price, int numberOfHours, FormationCategory category, User user,boolean newFormation,boolean bestSeller) {
        this.title = title;
        this.description = description;
        this.schedule = schedule;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.price = price;
        this.numberOfHours = numberOfHours;
        this.category = category;
        this.user = user;
        this.bestSeller = bestSeller;
        this.newFormation = newFormation;
        this.planning = planning;
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

    // Getter for schedule
    public Date getSchedule() {
        return schedule;
    }

    // Setter for schedule
    public void setSchedule(Date schedule) {
        this.schedule = schedule;
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

    public boolean isBestSeller() {
        return bestSeller;
    }

    public void setBestSeller(boolean bestSeller) {
        this.bestSeller = bestSeller;
    }

    public boolean isNewFormation() {
        return newFormation;
    }

    public void setNewFormation(boolean newFormation) {
        this.newFormation = newFormation;
    }
}