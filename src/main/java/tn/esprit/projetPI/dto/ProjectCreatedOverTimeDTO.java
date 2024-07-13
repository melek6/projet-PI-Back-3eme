package tn.esprit.projetPI.dto;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class ProjectCreatedOverTimeDTO {

    private LocalDate date;
    private Long projectCount;

    public ProjectCreatedOverTimeDTO(Date date, Long projectCount) {
        if (date != null) {
            this.date = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        this.projectCount = projectCount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getProjectCount() {
        return projectCount;
    }

    public void setProjectCount(Long projectCount) {
        this.projectCount = projectCount;
    }
}
