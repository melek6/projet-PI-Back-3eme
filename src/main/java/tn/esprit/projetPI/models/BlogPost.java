package tn.esprit.projetPI.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import tn.esprit.projetPI.security.BadWordUtils;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;



@Entity
@Table(name = "blog_posts")
public class BlogPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;

    private Date publishDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "blogPost", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Commentaire> commentaires;

    @OneToMany(mappedBy = "blogPost", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<React> reacts;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Commentaire> getCommentaires() {
        return commentaires;
    }

    public void setCommentaires(Set<Commentaire> commentaires) {
        this.commentaires = commentaires;
    }

    public Set<React> getReacts() {
        return reacts;
    }

    public void setReacts(Set<React> reacts) {
        this.reacts = reacts;
    }



}
