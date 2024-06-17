package tn.esprit.projetPI.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.projetPI.controllers.ResourceNotFoundException;
import tn.esprit.projetPI.models.Evaluation;
import tn.esprit.projetPI.models.Formation;
import tn.esprit.projetPI.repository.EvaluationRepository;
import tn.esprit.projetPI.repository.FormationRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FormationServiceImp implements FormationService {

    private final FormationRepository formationRepository;
    private final EvaluationRepository evaluationRepository;

    @Autowired
    public FormationServiceImp(FormationRepository formationRepository, EvaluationRepository evaluationRepository) {
        this.formationRepository = formationRepository;
        this.evaluationRepository = evaluationRepository;
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

    @Override
    public Evaluation addEvaluationToFormation(int formationId, Evaluation evaluation) {
        Optional<Formation> optionalFormation = formationRepository.findById(formationId);
        if (optionalFormation.isPresent()) {
            Formation formation = optionalFormation.get();
            evaluation.setFormation(formation);
            return evaluationRepository.save(evaluation);
        } else {
            throw new ResourceNotFoundException("Formation not found with id: " + formationId);
        }
    }

    @Override
    public List<Evaluation> getEvaluationsForFormation(int formationId) {
        Optional<Formation> optionalFormation = formationRepository.findById(formationId);
        if (optionalFormation.isPresent()) {
            Formation formation = optionalFormation.get();
            return formation.getEvaluations();
        } else {
            throw new ResourceNotFoundException("Formation not found with id: " + formationId);
        }
    }

    @Override
    public List<Formation> getBestSellerFormations() {
        return formationRepository.findByBestSeller(true);
    }

    @Override
    public List<Formation> getNewFormations() {
        return formationRepository.findByNewFormation(true);
    }
}
