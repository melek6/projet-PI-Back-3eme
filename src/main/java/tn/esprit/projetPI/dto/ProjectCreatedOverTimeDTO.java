package tn.esprit.projetPI.dto;

import java.time.LocalDate;
import java.util.Date;

public class ProjectCreatedOverTimeDTO {
    private Date date;
    private long projectCount;

    public ProjectCreatedOverTimeDTO(Date date, long projectCount) {
        this.date = date;
        this.projectCount = projectCount;
    }

    // Getters
    public Date getDate() {
        return date;
    }

    public long getProjectCount() {
        return projectCount;
    }

    // Setters
    public void setDate(Date date) {
        this.date = date;
    }

    public void setProjectCount(long projectCount) {
        this.projectCount = projectCount;
    }

}


