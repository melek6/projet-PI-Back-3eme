package tn.esprit.projetPI.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.projetPI.models.Question;
import tn.esprit.projetPI.models.Reponse;
import tn.esprit.projetPI.repository.QuestionRepository;
import tn.esprit.projetPI.repository.ReponseRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ReponseImpl {
    @Autowired
    private ReponseRepository reponseRepository;

    @Autowired
    private QuestionRepository questionRepository;

    public Reponse saveOrUpdateReponse(Reponse reponse, Long questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));
        reponse.setQuestion(question);
        return reponseRepository.save(reponse);
    }

    public Optional<Reponse> getReponseById(Long id) {
        return reponseRepository.findById(id);
    }

    public List<Reponse> getAllReponses() {
        return reponseRepository.findAll();
    }

    public void deleteReponseById(Long id) {
        reponseRepository.deleteById(id);
    }

    public Optional<Reponse> updateReponse(Long id, Reponse reponseDetails) {
        return reponseRepository.findById(id).map(reponse -> {
            reponse.setContent(reponseDetails.getContent());
            reponse.setIscorrect(reponseDetails.getIscorrect());
            reponse.setQuestion(questionRepository.findById(reponseDetails.getQuestion().getId())
                    .orElseThrow(() -> new RuntimeException("Question not found")));
            return reponseRepository.save(reponse);
        });
    }
}
