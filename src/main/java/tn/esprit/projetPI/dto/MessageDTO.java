package tn.esprit.projetPI.dto;

import java.time.LocalDateTime;


public class MessageDTO {
    private Long id;
    private Long userFromId;
    private String userFromUsername;
    private Long userToId;
    private String userToUsername;
    private String text;
    private LocalDateTime sendTime;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserFromId() {
        return userFromId;
    }

    public void setUserFromId(Long userFromId) {
        this.userFromId = userFromId;
    }

    public String getUserFromUsername() {
        return userFromUsername;
    }

    public void setUserFromUsername(String userFromUsername) {
        this.userFromUsername = userFromUsername;
    }

    public Long getUserToId() {
        return userToId;
    }

    public void setUserToId(Long userToId) {
        this.userToId = userToId;
    }

    public String getUserToUsername() {
        return userToUsername;
    }

    public void setUserToUsername(String userToUsername) {
        this.userToUsername = userToUsername;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getSendTime() {
        return sendTime;
    }

    public void setSendTime(LocalDateTime sendTime) {
        this.sendTime = sendTime;
    }
}
