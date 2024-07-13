package tn.esprit.projetPI.dto;

public class TopProjectOwnerDTO {
    private String username;
    private long projectCount;
    private double overallActivity;

    public TopProjectOwnerDTO(String username, long projectCount, double overallActivity) {
        this.username = username;
        this.projectCount = projectCount;
        this.overallActivity = overallActivity;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public long getProjectCount() {
        return projectCount;
    }

    public double getOverallActivity() {
        return overallActivity;
    }

    // Setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setProjectCount(long projectCount) {
        this.projectCount = projectCount;
    }

    public void setOverallActivity(double overallActivity) {
        this.overallActivity = overallActivity;
    }
}
