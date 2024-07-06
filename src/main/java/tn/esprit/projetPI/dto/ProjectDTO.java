package tn.esprit.projetPI.dto;

import tn.esprit.projetPI.models.User;
import java.time.LocalDate;

public class ProjectDTO {
    private Long id;
    private String title;
    private String description;
    private String category;
    private String skillsRequired;
    private LocalDate deadline;
    private double budget;
    private int nbPropositions;
    private User user;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSkillsRequired() {
        return skillsRequired;
    }

    public void setSkillsRequired(String skillsRequired) {
        this.skillsRequired = skillsRequired;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public int getNbPropositions() {
        return nbPropositions;
    }

    public void setNbPropositions(int nbPropositions) {
        this.nbPropositions = nbPropositions;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUserEmail() {
        return user != null ? user.getEmail() : null;
    }
}
