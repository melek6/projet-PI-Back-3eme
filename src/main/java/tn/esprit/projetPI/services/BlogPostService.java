package tn.esprit.projetPI.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.projetPI.models.BlogPost;
import tn.esprit.projetPI.repository.BlogPostRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BlogPostService implements IBlogPostService {

    @Autowired
    private BlogPostRepository blogPostRepository;

    @Override
    public List<BlogPost> getAllBlogPosts() {
        return blogPostRepository.findAll();
    }

    @Override
    public Optional<BlogPost> getBlogPostById(int id) {
        return blogPostRepository.findById(id);
    }

    @Override
    public BlogPost createBlogPost(BlogPost blogPost) {
        return blogPostRepository.save(blogPost);
    }

    @Override
    public BlogPost updateBlogPost(int id, BlogPost blogPostDetails) {
        Optional<BlogPost> blogPost = blogPostRepository.findById(id);
        if (blogPost.isPresent()) {
            BlogPost updatedBlogPost = blogPost.get();
            updatedBlogPost.setTitle(blogPostDetails.getTitle());
            updatedBlogPost.setContent(blogPostDetails.getContent());
            updatedBlogPost.setPublishDate(blogPostDetails.getPublishDate());
            return blogPostRepository.save(updatedBlogPost);
        } else {
            return null;
        }
    }

    @Override
    public void deleteBlogPost(int id) {
        if (blogPostRepository.existsById(id)) {
            blogPostRepository.deleteById(id);
        }
    }
}
