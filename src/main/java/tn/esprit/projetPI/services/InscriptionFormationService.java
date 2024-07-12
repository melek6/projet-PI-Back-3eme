package tn.esprit.projetPI.services;

import tn.esprit.projetPI.models.Etat;
import tn.esprit.projetPI.models.InscriptionFormation;

import java.util.List;
import java.util.Optional;

public interface InscriptionFormationService {
    List<InscriptionFormation> retrieveAllInscriptions();
    InscriptionFormation addInscription(InscriptionFormation inscriptionFormation);
    InscriptionFormation updateInscription(InscriptionFormation inscriptionFormation);
    Optional<InscriptionFormation> retrieveInscription(int id);
    void deleteInscription(int id);

    List<InscriptionFormation> getCompletedFormationsByUser(Long userId);

    List<InscriptionFormation> getInscriptionsByUserAndStatus(Long userId, Etat etat);
}
