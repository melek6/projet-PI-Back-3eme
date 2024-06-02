package tn.esprit.projetPI.services;

import tn.esprit.projetPI.models.BlogPost;

import java.util.List;
import java.util.Optional;

public interface IBlogPostService {
    List<BlogPost> getAllBlogPosts();
    Optional<BlogPost> getBlogPostById(int id);
    BlogPost createBlogPost(BlogPost blogPost);
    BlogPost updateBlogPost(int id, BlogPost blogPostDetails);
    void deleteBlogPost(int id);
}
