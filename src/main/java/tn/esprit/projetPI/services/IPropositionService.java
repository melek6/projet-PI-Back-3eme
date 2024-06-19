package tn.esprit.projetPI.services;

import tn.esprit.projetPI.models.Proposition;

import java.util.List;
import java.util.Optional;

public interface IPropositionService {
    List<Proposition> retrieveAllPropositions();
    Proposition addProposition(Proposition proposition);
    Proposition updateProposition(Long id, Proposition proposition);
    void deleteProposition(Long id);
    List<Proposition> getPropositionsByProjectId(Long projectId);
    Proposition approveProposition(Long id);
    Proposition declineProposition(Long id);
}
