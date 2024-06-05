package tn.esprit.projetPI.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.projetPI.models.Candidature;
import tn.esprit.projetPI.repository.CandidatureRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CandidatureServiceImpl implements CandidatureService{

    @Autowired
    private CandidatureRepository candidatureRepository;

    @Override
    public Candidature saveCandidature(Candidature candidature) {
        return candidatureRepository.save(candidature);
    }

    @Override
    public Candidature updateCandidature(Candidature candidature) {
        return candidatureRepository.save(candidature);
    }

    @Override
    public void deleteCandidature(int id) {
        candidatureRepository.deleteById(id);
    }

    @Override
    public Candidature getCandidatureById(int id) {
        Optional<Candidature> optionalCandidature = candidatureRepository.findById(id);
        return optionalCandidature.orElse(null);
    }

    @Override
    public List<Candidature> getAllCandidatures() {
        return candidatureRepository.findAll();
    }
}
