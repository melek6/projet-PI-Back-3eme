package tn.esprit.projetPI.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import tn.esprit.projetPI.models.BlogPost;
import tn.esprit.projetPI.models.User;
import tn.esprit.projetPI.repository.BlogPostRepository;
import tn.esprit.projetPI.repository.CommentaireRepository;
import tn.esprit.projetPI.repository.ReactRepository;
import tn.esprit.projetPI.repository.UserRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BlogPostService implements IBlogPostService {

    @Autowired
    private BlogPostRepository blogPostRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommentaireRepository commentRepository;


    @Autowired
    private BadWordServiceImp badWordService;

    @Autowired
    private CommentaireRepository commentaireRepository;

    @Autowired
    private ReactRepository reactRepository;

    @Override
    public List<BlogPost> getAllBlogPosts() {
        return blogPostRepository.findAll();
    }

    @Override
    public Optional<BlogPost> getBlogPostById(int id) {
        return blogPostRepository.findById(id);
    }

    @Override
    public BlogPost createBlogPost(BlogPost blogPost) throws Exception {
        if (badWordService.containsBadWord(blogPost.getContent()) || badWordService.containsBadWord(blogPost.getTitle())) {
            throw new Exception("Blog post contains inappropriate language.");
        }
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        Long userId;
        if (principal instanceof UserDetailsImpl) {
            userId = ((UserDetailsImpl) principal).getId();
        } else {
            throw new RuntimeException("L'utilisateur authentifié n'est pas trouvé ou n'est pas valide.");
        }

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé."));
        blogPost.setUser(user);
        blogPost.setPublishDate(new Date());

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

    @Override
    public long countComments(int blogPostId) {
        return commentRepository.countByBlogPostId(blogPostId);
    }


    @Override
    public long countLikes(int blogPostId) {
        return reactRepository.countByBlogPostIdAndReactionType(blogPostId, "like");
    }
    @Override
    public long countDislikes(int blogPostId) {
        return reactRepository.countByBlogPostIdAndReactionType(blogPostId, "dislik");
    }


}
