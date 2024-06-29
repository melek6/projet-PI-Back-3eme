package tn.esprit.projetPI.services;

import tn.esprit.projetPI.models.BlogPost;
import tn.esprit.projetPI.models.React;
import tn.esprit.projetPI.models.User;

import java.util.List;
import java.util.Optional;

public interface IReactService {
    List<React> getAllReacts();
    Optional<React> getReactById(int id);
    React createReact(React react);
    React updateReact(int id, React reactDetails);
    void deleteReact(int id);
    long countLikes(int blogPostId);
    long countDislikes(int blogPostId);
    Optional<React> findByBlogPostAndUser(BlogPost blogPost, User user);
}
