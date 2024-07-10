package tn.esprit.projetPI.models;

import javax.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_from_id", nullable = false)
    private User userFrom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_to_id", nullable = false)
    private User userTo;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private LocalDateTime sendTime;

    // Constructors
    public Message() {
    }

    public Message(User userFrom, User userTo, String text, LocalDateTime sendTime) {
        this.userFrom = userFrom;
        this.userTo = userTo;
        this.text = text;
        this.sendTime = sendTime;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(User userFrom) {
        this.userFrom = userFrom;
    }

    public User getUserTo() {
        return userTo;
    }

    public void setUserTo(User userTo) {
        this.userTo = userTo;
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
