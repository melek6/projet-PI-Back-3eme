package tn.esprit.projetPI.services;

import tn.esprit.projetPI.models.Proposition;
import tn.esprit.projetPI.models.User;

import java.util.List;

public interface IPropositionService {
    List<Proposition> retrieveAllPropositions();
    Proposition addProposition(Proposition proposition);
    Proposition updateProposition(Long id, Proposition proposition);
    void deleteProposition(Long id);
    List<Proposition> getPropositionsByProjectId(Long projectId);
    Proposition approveProposition(Long id, String username);
    Proposition declineProposition(Long id);
}
