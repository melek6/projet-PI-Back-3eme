package tn.esprit.projetPI.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "first_user_id", nullable = false)
    private User firstUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "second_user_id", nullable = false)
    private User secondUser;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messageList;

    public Chat() {
    }

    public Chat(User firstUser, User secondUser, List<Message> messageList) {
        this.firstUser = firstUser;
        this.secondUser = secondUser;
        this.messageList = messageList;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public User getFirstUser() {
        return firstUser;
    }

    public void setFirstUser(User firstUser) {
        this.firstUser = firstUser;
    }

    public User getSecondUser() {
        return secondUser;
    }

    public void setSecondUser(User secondUser) {
        this.secondUser = secondUser;
    }

    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }
}
