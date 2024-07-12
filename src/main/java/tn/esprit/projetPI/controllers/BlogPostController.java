package tn.esprit.projetPI.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import tn.esprit.projetPI.models.BlogPost;
import tn.esprit.projetPI.models.Commentaire;
import tn.esprit.projetPI.models.React;
import tn.esprit.projetPI.models.User;
import tn.esprit.projetPI.repository.BlogPostRepository;
import tn.esprit.projetPI.repository.CommentaireRepository;
import tn.esprit.projetPI.repository.ReactRepository;
import tn.esprit.projetPI.repository.UserRepository;
import tn.esprit.projetPI.services.IBlogPostService;
import tn.esprit.projetPI.services.ICommentaireService;
import tn.esprit.projetPI.services.IReactService;
import tn.esprit.projetPI.services.UserDetailsImpl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import java.util.Map;
import java.util.HashMap;


@RestController
@RequestMapping("/api/blogposts")
public class BlogPostController {
    @Autowired
    private RestTemplate restTemplate;


    @Value("${huggingface.api.key}")
    private String huggingFaceApiKey;

    @Autowired
    private BlogPostRepository blogPostRepository;

    @Autowired
    private IBlogPostService blogPostService;

    @Autowired
    private ICommentaireService commentaireService;

    @Autowired
    private IReactService reactService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    ReactRepository reactRepository;

    @Autowired
    private CommentaireRepository commentaireRepository;

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
    public ResponseEntity<BlogPost> createBlogPost(@RequestBody BlogPost blogPost) {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            Long userId;
            if (principal instanceof UserDetailsImpl) {
                userId = ((UserDetailsImpl) principal).getId();
            } else {
                throw new RuntimeException("L'utilisateur authentifié n'est pas trouvé ou n'est pas valide.");
            }

            User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé."));
            blogPost.setUser(user);
            blogPost.setPublishDate(new java.util.Date()); // Définir la date de création

            BlogPost createdBlogPost = blogPostService.createBlogPost(blogPost);
            return ResponseEntity.ok(createdBlogPost);
        } catch (Exception e) {
            return ResponseEntity.status(403).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<BlogPost> updateBlogPost(@PathVariable int id, @RequestBody BlogPost blogPostDetails) {
        BlogPost updatedBlogPost = blogPostService.updateBlogPost(id, blogPostDetails);
        if (updatedBlogPost != null) {
            return ResponseEntity.ok(updatedBlogPost);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlogPost(@PathVariable int id) {
        blogPostService.deleteBlogPost(id);
        return ResponseEntity.noContent().build();
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
        // Vérifie si une réaction similaire existe déjà pour l'utilisateur
        Optional<React> existingReact = reactRepository.findByBlogPostIdAndUserId(blogPostId, getUserId());

        if (existingReact.isPresent()) {
            React currentReact = existingReact.get();
            if (!currentReact.getType().equals(react.getType())) {
                currentReact.setType(react.getType());
                reactService.updateReact(currentReact.getId(), currentReact);
            }
            return ResponseEntity.ok(currentReact);
        } else {
            react.setBlogPost(blogPostService.getBlogPostById(blogPostId).orElse(null));
            react.setUser(userRepository.findById(getUserId()).orElse(null));
            React savedReact = reactService.createReact(react);
            return ResponseEntity.ok(savedReact);
        }
    }

    private Long getUserId() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getId();
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

    @GetMapping("/{blogPostId}/reacts/user")
    public ResponseEntity<React> getUserReact(@PathVariable int blogPostId) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId;

        if (principal instanceof UserDetailsImpl) {
            userId = ((UserDetailsImpl) principal).getId();
        } else {
            return ResponseEntity.status(403).body(null);
        }

        Optional<React> react = reactRepository.findByBlogPostIdAndUserId(blogPostId, userId);
        return react.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{blogPostId}/comments")
    public ResponseEntity<List<Commentaire>> getCommentsForBlogPost(@PathVariable int blogPostId) {
        List<Commentaire> commentaires = commentaireRepository.findByBlogPostId(blogPostId);
        return ResponseEntity.ok(commentaires);
    }

    @DeleteMapping("/deleteComments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable int commentId) {
        if (commentaireRepository.existsById(commentId)) {
            commentaireRepository.deleteById(commentId);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/comments/{commentId}")
    public ResponseEntity<Commentaire> updateComment(@PathVariable int commentId, @RequestBody Commentaire commentDetails) {
        Optional<Commentaire> existingComment = commentaireRepository.findById(commentId);
        if (existingComment.isPresent()) {
            Commentaire updatedComment = existingComment.get();
            updatedComment.setContent(commentDetails.getContent());
            Commentaire savedComment = commentaireRepository.save(updatedComment);
            return ResponseEntity.ok(savedComment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/comments/{commentId}/reply")
    public ResponseEntity<Commentaire> addReplyToComment(@PathVariable int commentId, @RequestBody Commentaire reply) {
        try {
            Optional<Commentaire> parentComment = commentaireRepository.findById(commentId);
            if (parentComment.isPresent()) {
                // Récupérer l'utilisateur connecté
                Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                Long userId;
                if (principal instanceof UserDetailsImpl) {
                    userId = ((UserDetailsImpl) principal).getId();
                } else {
                    throw new RuntimeException("L'utilisateur authentifié n'est pas trouvé ou n'est pas valide.");
                }
                User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé."));

                // Assigner l'utilisateur et le blogPost au commentaire de réponse
                Commentaire parent = parentComment.get();
                reply.setParentComment(parent);
                reply.setBlogPost(parent.getBlogPost());
                reply.setUser(user); // Assigner l'utilisateur
                reply.setDate(new Date()); // Définir la date de création de la réponse

                Commentaire savedReply = commentaireRepository.save(reply);
                return ResponseEntity.ok(savedReply);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }




    @PostMapping("/rephrase")
    public ResponseEntity<String> rephraseContent(@RequestBody Map<String, String> request) {
        String content = request.get("content");
        String aiApiUrl = "https://api-inference.huggingface.co/models/gpt2";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(huggingFaceApiKey);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("inputs", "Rephrase the following content: " + content);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(aiApiUrl, HttpMethod.POST, entity, String.class);
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            return ResponseEntity.status(500).body("Error while communicating with Hugging Face API: " + errorMessage);
        }
    }


}
