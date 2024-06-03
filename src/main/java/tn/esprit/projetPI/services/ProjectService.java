package tn.esprit.projetPI.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.projetPI.models.Project;
import tn.esprit.projetPI.models.ProjectCategory;
import tn.esprit.projetPI.repository.ProjectRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService implements IProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public List<Project> retrieveAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public Project addProject(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public Project updateProject(Long id, Project projectDetails) {
        Project existingProject = projectRepository.findById(id).orElseThrow(() -> new RuntimeException("Project not found"));

        if (projectDetails.getTitle() != null) {
            existingProject.setTitle(projectDetails.getTitle());
        }
        if (projectDetails.getDescription() != null) {
            existingProject.setDescription(projectDetails.getDescription());
        }
        if (projectDetails.getCategory() != null) {
            existingProject.setCategory(projectDetails.getCategory());
        }
        if (projectDetails.getSkillsRequired() != null) {
            existingProject.setSkillsRequired(projectDetails.getSkillsRequired());
        }
        if (projectDetails.getDeadline() != null) {
            existingProject.setDeadline(projectDetails.getDeadline());
        }
        if (projectDetails.getBudget() != 0) {
            existingProject.setBudget(projectDetails.getBudget());
        }

        return projectRepository.save(existingProject);
    }

    @Override
    public Optional<Project> retrieveProject(Long id) {
        return projectRepository.findById(id);
    }

    @Override
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

    @Override
    public List<Project> searchProjects(ProjectCategory category, String skillsRequired) {
        return projectRepository.searchProjects(category, skillsRequired);
    }
}
