package tn.esprit.projetPI.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.projetPI.models.BlogPost;
import tn.esprit.projetPI.models.Commentaire;
import tn.esprit.projetPI.models.React;
import tn.esprit.projetPI.repository.BlogPostRepository;
import tn.esprit.projetPI.services.IBlogPostService;
import tn.esprit.projetPI.services.ICommentaireService;
import tn.esprit.projetPI.services.IReactService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/blogposts")
public class BlogPostController {

    @Autowired
    private BlogPostRepository blogPostRepository;

    @Autowired
    private IBlogPostService blogPostService;

    @Autowired
    private ICommentaireService commentaireService;

    @Autowired
    private IReactService reactService;

    @GetMapping
    public List<BlogPost> getAllBlogPosts() {
        return blogPostRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogPost> getBlogPostById(@PathVariable int id) {
        Optional<BlogPost> blogPost = blogPostRepository.findById(id);
        return blogPost.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public BlogPost createBlogPost(@RequestBody BlogPost blogPost) {
        return blogPostRepository.save(blogPost);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BlogPost> updateBlogPost(@PathVariable int id, @RequestBody BlogPost blogPostDetails) {
        Optional<BlogPost> blogPost = blogPostRepository.findById(id);
        if (blogPost.isPresent()) {
            BlogPost updatedBlogPost = blogPost.get();
            updatedBlogPost.setTitle(blogPostDetails.getTitle());
            updatedBlogPost.setContent(blogPostDetails.getContent());
            updatedBlogPost.setPublishDate(blogPostDetails.getPublishDate());
            return ResponseEntity.ok(blogPostRepository.save(updatedBlogPost));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlogPost(@PathVariable int id) {
        if (blogPostRepository.existsById(id)) {
            blogPostRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{blogPostId}/comments")
    public ResponseEntity<Commentaire> addCommentToBlogPost(@PathVariable int blogPostId, @RequestBody Commentaire commentaire) {
        Optional<BlogPost> blogPost = blogPostService.getBlogPostById(blogPostId);
        if (blogPost.isPresent()) {
            commentaire.setBlogPost(blogPost.get());
            Commentaire savedCommentaire = commentaireService.createCommentaire(commentaire);
            return ResponseEntity.ok(savedCommentaire);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/{blogPostId}/reacts")
    public ResponseEntity<React> addReactToBlogPost(@PathVariable int blogPostId, @RequestBody React react) {
        Optional<BlogPost> blogPost = blogPostService.getBlogPostById(blogPostId);
        if (blogPost.isPresent()) {
            react.setBlogPost(blogPost.get());
            React savedReact = reactService.createReact(react);
            return ResponseEntity.ok(savedReact);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/{blogPostId}/likes")
    public ResponseEntity<Long> getLikesCount(@PathVariable int blogPostId) {
        long likes = reactService.countLikes(blogPostId);
        return ResponseEntity.ok(likes);
    }

    @GetMapping("/{blogPostId}/dislikes")
    public ResponseEntity<Long> getDislikesCount(@PathVariable int blogPostId) {
        long dislikes = reactService.countDislikes(blogPostId);
        return ResponseEntity.ok(dislikes);
    }

}
