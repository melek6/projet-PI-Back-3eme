package tn.esprit.projetPI.services;

import tn.esprit.projetPI.dto.PropositionDTO;
import tn.esprit.projetPI.models.Proposition;

import java.util.List;

public interface IPropositionService {
    List<PropositionDTO> retrieveAllPropositions();
    Proposition addProposition(Proposition proposition);
    Proposition updateProposition(Long id, Proposition propositionDetails);
    void deleteProposition(Long id);
    List<PropositionDTO> getPropositionsByProjectId(Long projectId);
    Proposition approveProposition(Long id, String username);
    Proposition declineProposition(Long id);
    List<PropositionDTO.UserDTO> getUsersWithApprovedPropositions();
    List<PropositionDTO.UserDTO> getUsersWithApprovedPropositionsForProjectOwner(String ownerUsername);
}
