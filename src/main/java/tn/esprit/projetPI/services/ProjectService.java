package tn.esprit.projetPI.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.projetPI.dto.DtoMapper;
import tn.esprit.projetPI.dto.ProjectDTO;
import tn.esprit.projetPI.models.Project;
import tn.esprit.projetPI.models.ProjectCategory;
import tn.esprit.projetPI.models.User;
import tn.esprit.projetPI.repository.ProjectRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectService implements IProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public List<ProjectDTO> retrieveAllProjects() {
        List<Project> projects = projectRepository.findAll();
        return projects.stream()
                .map(DtoMapper::toProjectDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Project addProject(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public Project updateProject(Long id, Project projectDetails) {
        Project existingProject = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        existingProject.setTitle(projectDetails.getTitle());
        existingProject.setDescription(projectDetails.getDescription());
        existingProject.setCategory(projectDetails.getCategory());
        existingProject.setSkillsRequired(projectDetails.getSkillsRequired());
        existingProject.setDeadline(projectDetails.getDeadline());
        existingProject.setBudget(projectDetails.getBudget());

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
        // Implement search logic here if needed
        return null;
    }

    @Override
    public List<Project> retrieveProjectsByUser(User user) {
        return projectRepository.findByUser(user);
    }
}
