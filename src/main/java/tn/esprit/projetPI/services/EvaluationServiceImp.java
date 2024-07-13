package tn.esprit.projetPI.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.projetPI.models.Evaluation;
import tn.esprit.projetPI.repository.EvaluationRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EvaluationServiceImp implements EvaluationService {

    private final EvaluationRepository evaluationRepository;

    @Autowired
    public EvaluationServiceImp(EvaluationRepository evaluationRepository) {
        this.evaluationRepository = evaluationRepository;
    }
    public List<Evaluation> getEvaluationsByFormation(int formationId) {
        return evaluationRepository.findByFormationId(formationId);
    }
    @Override
    public List<Evaluation> getAllEvaluations() {
        return evaluationRepository.findAll();
    }

    @Override
    public Evaluation addEvaluation(Evaluation evaluation) {
        return evaluationRepository.save(evaluation);
    }

    @Override
    public Evaluation updateEvaluation(Evaluation evaluation) {
        return evaluationRepository.save(evaluation);
    }

    @Override
    public Optional<Evaluation> getEvaluationById(int id) {
        return evaluationRepository.findById(id);
    }

    @Override
    public void deleteEvaluation(int id) {
        evaluationRepository.deleteById(id);
    }
}
