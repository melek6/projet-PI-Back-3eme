package tn.esprit.projetPI.dto;

public class UserActivityDTO {
    private String username;
    private long projectCount;
    private long overallActivity;

    public UserActivityDTO(String username, long projectCount, long overallActivity) {
        this.username = username;
        this.projectCount = projectCount;
        this.overallActivity = overallActivity;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getProjectCount() {
        return projectCount;
    }

    public void setProjectCount(long projectCount) {
        this.projectCount = projectCount;
    }

    public long getOverallActivity() {
        return overallActivity;
    }

    public void setOverallActivity(long overallActivity) {
        this.overallActivity = overallActivity;
    }
}
