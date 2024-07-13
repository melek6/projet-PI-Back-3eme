package tn.esprit.projetPI.services;

// StatsService.java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.projetPI.dto.ProjectActivityDTO;
import tn.esprit.projetPI.dto.ProjectCreatedOverTimeDTO;
import tn.esprit.projetPI.dto.TopProjectOwnerDTO;
import tn.esprit.projetPI.repository.ProjectRepository;
import tn.esprit.projetPI.repository.PropositionRepository;
import tn.esprit.projetPI.repository.UserRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatsService implements IStatsService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private PropositionRepository propositionRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Map<String, Long> getProjectsByCategory() {
        List<Object[]> results = projectRepository.countProjectsByCategory();
        Map<String, Long> categoryCounts = new HashMap<>();
        for (Object[] result : results) {
            categoryCounts.put((String) result[0], (Long) result[1]);
        }
        return categoryCounts;
    }

    @Override
    public Map<String, Long> getPropositionsByStatus() {
        List<Object[]> results = propositionRepository.countPropositionsByStatus();
        Map<String, Long> statusCounts = new HashMap<>();
        for (Object[] result : results) {
            statusCounts.put((String) result[0], (Long) result[1]);
        }
        return statusCounts;
    }

    @Override
    public long countProjects() {
        return projectRepository.count();
    }

    @Override
    public long countApprovedPropositions() {
        return propositionRepository.countByStatus("APPROVED");
    }

    @Override
    public List<ProjectActivityDTO> getRecentProjectActivities() {
        return projectRepository.findRecentProjectActivities();
    }

    @Override
    public List<TopProjectOwnerDTO> getTopProjectOwners() {
        return projectRepository.findTopProjectOwners();
    }

    @Override
    public List<ProjectCreatedOverTimeDTO> getProjectsCreatedOverTime() {
        return projectRepository.findProjectsCreatedOverTime();
    }
}
