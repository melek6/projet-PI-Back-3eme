package tn.esprit.projetPI.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import tn.esprit.projetPI.models.BlogPost;
import tn.esprit.projetPI.models.React;
import tn.esprit.projetPI.models.User;
import tn.esprit.projetPI.repository.ReactRepository;
import tn.esprit.projetPI.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ReactService implements IReactService {

    @Autowired
    private ReactRepository reactRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<React> getAllReacts() {
        return reactRepository.findAll();
    }

    @Override
    public Optional<React> getReactById(int id) {
        return reactRepository.findById(id);
    }

    @Override
    public React createReact(React react) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Long userId;
        if (principal instanceof UserDetailsImpl) {
            userId = ((UserDetailsImpl) principal).getId();
        } else {
            throw new RuntimeException("L'utilisateur authentifié n'est pas trouvé ou n'est pas valide.");
        }

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé."));
        react.setUser(user);

        return reactRepository.save(react);
    }

    @Override
    public React updateReact(int id, React reactDetails) {
        Optional<React> react = reactRepository.findById(id);
        if (react.isPresent()) {
            React updatedReact = react.get();
            updatedReact.setType(reactDetails.getType());
            return reactRepository.save(updatedReact);
        } else {
            return null;
        }
    }

    @Override
    public void deleteReact(int id) {
        if (reactRepository.existsById(id)) {
            reactRepository.deleteById(id);
        }
    }

    @Override
    public long countLikes(int blogPostId) {
        return reactRepository.countByBlogPostIdAndType(blogPostId, "like");
    }

    @Override
    public long countDislikes(int blogPostId) {
        return reactRepository.countByBlogPostIdAndType(blogPostId, "dislike");
    }

    @Override
    public Optional<React> findByBlogPostAndUser(BlogPost blogPost, User user) {
        return reactRepository.findByBlogPostAndUser(blogPost, user);
    }
}
