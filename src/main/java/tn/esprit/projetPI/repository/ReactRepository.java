package tn.esprit.projetPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.projetPI.models.React;
@Repository
public interface ReactRepository extends JpaRepository<React, Integer> {
    long countByBlogPostIdAndType(int blogPostId, String type);

}