package tn.esprit.projetPI.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import tn.esprit.projetPI.models.Project;
import tn.esprit.projetPI.models.ProjectCategory;
import tn.esprit.projetPI.models.User;
import tn.esprit.projetPI.repository.UserRepository;
import tn.esprit.projetPI.security.jwt.JwtUtils;
import tn.esprit.projetPI.services.IProjectService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = "http://localhost:4200")
public class ProjectController {

    @Autowired
    private IProjectService projectService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/all")
    public List<Project> getAllProjects() {
        return projectService.retrieveAllProjects();
    }

    @GetMapping("/view/{id}")
    public Project getProjectById(@PathVariable Long id) {
        Optional<Project> project = projectService.retrieveProject(id);
        return project.orElse(null);
    }

    @PostMapping("/create")
    public Project createProject(@RequestBody Project project) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        project.setUser(user);
        return projectService.addProject(project);
    }

    @PutMapping("/update/{id}")
    public Project updateProject(@PathVariable Long id, @RequestBody Project projectDetails) {
        return projectService.updateProject(id, projectDetails);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
    }

    @GetMapping("/search")
    public List<Project> searchProjects(@RequestParam(required = false) ProjectCategory category,
                                        @RequestParam(required = false) String skillsRequired) {
        return projectService.searchProjects(category, skillsRequired);
    }
}
