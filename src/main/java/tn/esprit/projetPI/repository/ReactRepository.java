package tn.esprit.projetPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.projetPI.models.BlogPost;
import tn.esprit.projetPI.models.React;
import tn.esprit.projetPI.models.User;

import java.util.Optional;

@Repository
public interface ReactRepository extends JpaRepository<React, Integer> {
    Optional<React> findByBlogPostAndUser(BlogPost blogPost, User user);
   // long countByBlogPostIdAndType(int blogPostId, String type);


    @Query("SELECT COUNT(r) FROM React r WHERE r.blogPost.id = :blogPostId AND r.type = :reactionType")
    long countByBlogPostIdAndReactionType(@Param("blogPostId") int blogPostId, @Param("reactionType") String reactionType);





    // Si vous utilisez des types distincts pour likes et dislikes, vous pouvez ajouter ces méthodes
        @Query("SELECT r FROM React r WHERE r.blogPost.id = :blogPostId AND r.user.id = :userId")
        Optional<React> findByBlogPostIdAndUserId(@Param("blogPostId") int blogPostId, @Param("userId") Long userId);
    }


