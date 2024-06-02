package tn.esprit.projetPI.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.projetPI.models.React;
import tn.esprit.projetPI.repository.ReactRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ReactService implements IReactService {

    @Autowired
    private ReactRepository reactRepository;

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
    }

