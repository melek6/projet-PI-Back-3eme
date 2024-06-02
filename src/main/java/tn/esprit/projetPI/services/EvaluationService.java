package tn.esprit.projetPI.services;

import tn.esprit.projetPI.models.Evaluation;

import java.util.List;
import java.util.Optional;

public interface EvaluationService {
    List<Evaluation> getAllEvaluations();
    Evaluation addEvaluation(Evaluation evaluation);
    Evaluation updateEvaluation(Evaluation evaluation);
    Optional<Evaluation> getEvaluationById(int id);
    void deleteEvaluation(int id);
}
