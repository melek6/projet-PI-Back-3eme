package tn.esprit.projetPI.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.projetPI.models.Proposition;
import tn.esprit.projetPI.models.Project;
import tn.esprit.projetPI.repository.PropositionRepository;
import tn.esprit.projetPI.repository.ProjectRepository;

import java.util.List;

@Service
public class PropositionService implements IPropositionService {

    @Autowired
    private PropositionRepository propositionRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public List<Proposition> retrieveAllPropositions() {
        return propositionRepository.findAll();
    }

    @Override
    public Proposition addProposition(Proposition proposition) {
        proposition.setStatus("PENDING"); // Set default status
        return propositionRepository.save(proposition);
    }

    @Override
    public Proposition updateProposition(Long id, Proposition propositionDetails) {
        Proposition existingProposition = propositionRepository.findById(id).orElseThrow(() -> new RuntimeException("Proposition not found"));

        if (propositionDetails.getDetail() != null) {
            existingProposition.setDetail(propositionDetails.getDetail());
        }
        if (propositionDetails.getAmount() != 0) {
            existingProposition.setAmount(propositionDetails.getAmount());
        }
        if (propositionDetails.getDate() != null) {
            existingProposition.setDate(propositionDetails.getDate());
        }

        return propositionRepository.save(existingProposition);
    }

    @Override
    public void deleteProposition(Long id) {
        propositionRepository.deleteById(id);
    }

    @Override
    public List<Proposition> getPropositionsByProjectId(Long projectId) {
        return propositionRepository.findByProjectId(projectId);
    }

    @Override
    public Proposition approveProposition(Long id, String username) {
        Proposition proposition = propositionRepository.findById(id).orElseThrow(() -> new RuntimeException("Proposition not found"));
        Project project = proposition.getProject();

        if (!project.getUser().getUsername().equals(username)) {
            throw new RuntimeException("You are not authorized to approve this proposition.");
        }

        proposition.setStatus("APPROVED");
        return propositionRepository.save(proposition);
    }

    @Override
    public Proposition declineProposition(Long id) {
        Proposition proposition = propositionRepository.findById(id).orElseThrow(() -> new RuntimeException("Proposition not found"));
        proposition.setStatus("DECLINED");
        return propositionRepository.save(proposition);
    }
}
