package tn.esprit.projetPI.dto;

public class NotificationDTO {
    private Long id;
    private String message;

    // Constructor
    public NotificationDTO(Long id, String message) {
        this.id = id;
        this.message = message;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}