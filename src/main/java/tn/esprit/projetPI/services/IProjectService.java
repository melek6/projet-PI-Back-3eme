package tn.esprit.projetPI.services;

import tn.esprit.projetPI.dto.ProjectDTO;
import tn.esprit.projetPI.models.Project;
import tn.esprit.projetPI.models.ProjectCategory;
import tn.esprit.projetPI.models.User;

import java.util.List;
import java.util.Optional;

public interface IProjectService {
    List<ProjectDTO> retrieveAllProjects();
    Project addProject(Project project);
    Project updateProject(Long id, Project projectDetails);
    Optional<Project> retrieveProject(Long id);
    void deleteProject(Long id);
    List<Project> retrieveProjectsByUser(User user);
    List<Project> searchProjects(ProjectCategory category, Double minBudget, Double maxBudget);
}
