package tn.esprit.projetPI.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.projetPI.models.Formation;
import tn.esprit.projetPI.repository.FormationRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FormationServiceImp implements FormationService {

    private final FormationRepository formationRepository;

    @Autowired
    public FormationServiceImp(FormationRepository formationRepository) {
        this.formationRepository = formationRepository;
    }

    @Override
    public List<Formation> retrieveAllFormations() {
        return formationRepository.findAll();
    }

    @Override
    public Formation addFormation(Formation formation) {
        return formationRepository.save(formation);
    }

    @Override
    public Formation updateFormation(Formation formation) {
        return formationRepository.save(formation);
    }

    @Override
    public Optional<Formation> retrieveFormation(int id) {
        return formationRepository.findById(id);
    }

    @Override
    public void deleteFormation(int id) {
        formationRepository.deleteById(id);
    }
}