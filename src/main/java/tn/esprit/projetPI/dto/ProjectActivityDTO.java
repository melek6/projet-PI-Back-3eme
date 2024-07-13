package tn.esprit.projetPI.dto;


import java.time.LocalDateTime;

public class ProjectActivityDTO {
    private String activityType;
    private String projectName;
    private LocalDateTime createdAt;

    public ProjectActivityDTO(String activityType, String projectName, LocalDateTime createdAt) {
        this.activityType = activityType;
        this.projectName = projectName;
        this.createdAt = createdAt;
    }

    // Getters
    public String getActivityType() {
        return activityType;
    }

    public String getProjectName() {
        return projectName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // Setters
    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
