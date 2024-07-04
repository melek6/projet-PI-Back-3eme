package tn.esprit.projetPI.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import tn.esprit.projetPI.dto.ProjectDTO;
import tn.esprit.projetPI.models.Project;
import tn.esprit.projetPI.models.ProjectCategory;
import tn.esprit.projetPI.models.User;
import tn.esprit.projetPI.repository.UserRepository;
import tn.esprit.projetPI.services.EmailService;
import tn.esprit.projetPI.services.IProjectService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = "http://localhost:4200")
public class ProjectController {

    @Autowired
    private IProjectService projectService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @GetMapping("/all")
    public List<ProjectDTO> getAllProjects() {
        return projectService.retrieveAllProjects();
    }

    @GetMapping("/view/{id}")
    public Project getProjectById(@PathVariable Long id) {
        Optional<Project> project = projectService.retrieveProject(id);
        return project.orElse(null);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createProject(
            @RequestParam @NotBlank(message = "Title is mandatory") String title,
            @RequestParam @NotBlank(message = "Description is mandatory") String description,
            @RequestParam @NotNull(message = "Category is mandatory") ProjectCategory category,
            @RequestParam @NotBlank(message = "Skills required is mandatory") String skillsRequired,
            @RequestParam @NotNull(message = "Deadline is mandatory") String deadline,
            @RequestParam @NotNull(message = "Budget is mandatory") double budget
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

        LocalDate deadlineDate = LocalDate.parse(deadline);

        Project project = new Project(
                title,
                description,
                category,
                skillsRequired,
                deadlineDate,
                budget,
                user
        );

        Project createdProject = projectService.addProject(project);
        emailService.sendSimpleEmail(user.getEmail(), "Project Created", "Your project has been created successfully!");

        return new ResponseEntity<>(createdProject, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Project> updateProject(
            @PathVariable Long id,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam ProjectCategory category,
            @RequestParam String skillsRequired,
            @RequestParam String deadline,
            @RequestParam double budget) {
        Project projectDetails = new Project();
        projectDetails.setTitle(title);
        projectDetails.setDescription(description);
        projectDetails.setCategory(category);
        projectDetails.setSkillsRequired(skillsRequired);
        projectDetails.setDeadline(LocalDate.parse(deadline)); // Assuming deadline is in YYYY-MM-DD format
        projectDetails.setBudget(budget);
        Project updatedProject = projectService.updateProject(id, projectDetails);
        if (updatedProject == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedProject);
    }

    @GetMapping("/categories")
    public List<String> getCategories() {
        return Arrays.stream(ProjectCategory.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search")
    public List<Project> searchProjects(
            @RequestParam(required = false) ProjectCategory category,
            @RequestParam(required = false) Double minBudget,
            @RequestParam(required = false) Double maxBudget) {
        return projectService.searchProjects(category, minBudget, maxBudget);
    }

    @GetMapping("/user/GetById")
    public List<Project> getProjectsByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        return projectService.retrieveProjectsByUser(user);
    }

    @GetMapping("/send-reminders")
    public ResponseEntity<?> sendDeadlineReminders() {
        List<ProjectDTO> projects = projectService.retrieveAllProjects();
        LocalDate now = LocalDate.now();

        for (ProjectDTO project : projects) {
            if (project.getDeadline() != null && project.getDeadline().isAfter(now) && project.getDeadline().isBefore(now.plusWeeks(1))) {
                String userEmail = project.getUserEmail();
                if (userEmail != null) {
                    emailService.sendDeadlineReminderEmail(userEmail, project);
                }
            }
        }
        return new ResponseEntity<>("Reminders sent", HttpStatus.OK);
    }

    // Exception handling methods

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        bindingResult.getAllErrors().forEach((error) -> {
            String fieldName = ((org.springframework.validation.FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeExceptions(RuntimeException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
