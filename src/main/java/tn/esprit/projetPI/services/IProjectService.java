package tn.esprit.projetPI.services;

import tn.esprit.projetPI.models.Project;
import tn.esprit.projetPI.models.ProjectCategory;

import java.util.List;
import java.util.Optional;

public interface IProjectService {
    List<Project> retrieveAllProjects();
    Project addProject(Project project);
    Project updateProject(Long id, Project project);
    Optional<Project> retrieveProject(Long id);
    void deleteProject(Long id);
    List<Project> searchProjects(ProjectCategory category, String skillsRequired);
}
