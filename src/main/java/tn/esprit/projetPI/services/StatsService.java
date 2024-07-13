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

import java.util.*;

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
            categoryCounts.put(result[0].toString(), (Long) result[1]); // Convert enum to string
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
        List<Object[]> results = projectRepository.findProjectsCreatedOverTimeNative();
        List<ProjectCreatedOverTimeDTO> projectsCreatedOverTimeDTOs = new ArrayList<>();

        for (Object[] result : results) {
            Date date = (Date) result[0];
            Long projectCount = ((Number) result[1]).longValue();
            projectsCreatedOverTimeDTOs.add(new ProjectCreatedOverTimeDTO(date, projectCount));
        }

        return projectsCreatedOverTimeDTOs;
    }

}
