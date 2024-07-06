package tn.esprit.projetPI.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.projetPI.dto.DtoMapper;
import tn.esprit.projetPI.dto.PropositionDTO;
import tn.esprit.projetPI.models.Proposition;
import tn.esprit.projetPI.repository.PropositionRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PropositionService implements IPropositionService {

    @Autowired
    private PropositionRepository propositionRepository;

    @Autowired
    private EmailService emailService;

    @Override
    public List<PropositionDTO> retrieveAllPropositions() {
        List<Proposition> propositions = propositionRepository.findAll();
        return propositions.stream()
                .map(DtoMapper::toPropositionDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PropositionDTO> getPropositionsByProjectId(Long projectId) {
        List<Proposition> propositions = propositionRepository.findByProjectId(projectId);
        return propositions.stream()
                .map(DtoMapper::toPropositionDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Proposition addProposition(Proposition proposition) {
        return propositionRepository.save(proposition);
    }

    @Override
    public Proposition updateProposition(Long id, Proposition propositionDetails) {
        Proposition existingProposition = propositionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proposition not found"));

        existingProposition.setDetail(propositionDetails.getDetail());
        existingProposition.setAmount(propositionDetails.getAmount());
        existingProposition.setStatus(propositionDetails.getStatus());

        return propositionRepository.save(existingProposition);
    }

    @Override
    public void deleteProposition(Long id) {
        propositionRepository.deleteById(id);
    }

    @Override
    public Proposition approveProposition(Long id, String username) {
        Proposition proposition = propositionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proposition not found"));
        proposition.setStatus("APPROVED");
        Proposition approvedProposition = propositionRepository.save(proposition);

        // Send email notification
        emailService.sendPropositionStatusEmail(proposition.getUser().getEmail(), "APPROVED");

        return approvedProposition;
    }

    @Override
    public Proposition declineProposition(Long id) {
        Proposition proposition = propositionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proposition not found"));
        proposition.setStatus("DECLINED");
        Proposition declinedProposition = propositionRepository.save(proposition);

        // Send email notification
        emailService.sendPropositionStatusEmail(proposition.getUser().getEmail(), "DECLINED");

        return declinedProposition;
    }
}
