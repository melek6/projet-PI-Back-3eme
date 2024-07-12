package tn.esprit.projetPI.services;


import tn.esprit.projetPI.dto.PropositionDTO;
import tn.esprit.projetPI.models.Proposition;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

public interface IPropositionService {

    List<PropositionDTO> retrieveAllPropositions();

    List<PropositionDTO> getPropositionsByProjectId(Long projectId);

    Proposition addProposition(Proposition proposition);

    Proposition updateProposition(Long id, String detail, double amount, MultipartFile file, boolean removeExistingFile);

    void deleteProposition(Long id);

    Proposition approveProposition(Long id, String username);

    Proposition declineProposition(Long id);

    List<PropositionDTO.UserDTO> getUsersWithApprovedPropositions();

    List<PropositionDTO.UserDTO> getUsersWithApprovedPropositionsForProjectOwner(String ownerUsername);

    List<PropositionDTO> getPropositionsByUser(String username);

    void deleteUserProposition(Long id, String username);

    Proposition updateUserProposition(Long id, String username, String detail, double amount, MultipartFile file, boolean removeExistingFile);

    String uploadFileToFirebase(MultipartFile file);

    Optional<Proposition> getPropositionById(Long id);

    List<PropositionDTO> getApprovedPropositions();

    void deleteFileFromProposition(Long id);

}
