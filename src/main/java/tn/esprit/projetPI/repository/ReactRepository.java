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
    long countByBlogPostIdAndType(int blogPostId, String type);
    Optional<React> findByBlogPostAndUser(BlogPost blogPost, User user);
        @Query("SELECT r FROM React r WHERE r.blogPost.id = :blogPostId AND r.user.id = :userId")
        Optional<React> findByBlogPostIdAndUserId(@Param("blogPostId") int blogPostId, @Param("userId") Long userId);
    }


