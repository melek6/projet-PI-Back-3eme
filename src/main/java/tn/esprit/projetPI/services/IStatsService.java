package tn.esprit.projetPI.services;

import tn.esprit.projetPI.dto.ProjectActivityDTO;
import tn.esprit.projetPI.dto.ProjectCreatedOverTimeDTO;
import tn.esprit.projetPI.dto.TopProjectOwnerDTO;

import java.util.List;
import java.util.Map;

public interface IStatsService {
    Map<String, Long> getProjectsByCategory();
    Map<String, Long> getPropositionsByStatus();
    long countApprovedPropositions();
    long countProjects();
    List<ProjectActivityDTO> getRecentProjectActivities();
    List<TopProjectOwnerDTO> getTopProjectOwners();
    List<ProjectCreatedOverTimeDTO> getProjectsCreatedOverTime();
}
