package tn.esprit.projetPI.services;

import tn.esprit.projetPI.models.Evaluation;
import tn.esprit.projetPI.models.Formation;

import java.util.List;
import java.util.Optional;

public interface FormationService {
    List<Formation> retrieveAllFormations();
    Formation addFormation(Formation formation);
    Formation updateFormation(Formation formation);
    Optional<Formation> retrieveFormation(int id);
    void deleteFormation(int id);

    Evaluation addEvaluationToFormation(int formationId, Evaluation evaluation);
    List<Evaluation> getEvaluationsForFormation(int formationId);
}
