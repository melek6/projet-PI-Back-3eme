package tn.esprit.projetPI.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import tn.esprit.projetPI.dto.ProjectActivityDTO;
import tn.esprit.projetPI.dto.ProjectCreatedOverTimeDTO;
import tn.esprit.projetPI.dto.TopProjectOwnerDTO;
import tn.esprit.projetPI.services.IStatsService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/stats")
@CrossOrigin(origins = "http://localhost:4200")
public class StatsController {

    @Autowired
    private IStatsService statsService;

    @GetMapping("/projects-by-category")
    public ResponseEntity<Map<String, Long>> getProjectsByCategory() {
        Map<String, Long> projectsByCategory = statsService.getProjectsByCategory();
        return ResponseEntity.ok(projectsByCategory);
    }

    @GetMapping("/propositions-by-status")
    public ResponseEntity<Map<String, Long>> getPropositionsByStatus() {
        Map<String, Long> propositionsByStatus = statsService.getPropositionsByStatus();
        return ResponseEntity.ok(propositionsByStatus);
    }

    @GetMapping("/total-projects")
    public ResponseEntity<Long> getTotalProjects() {
        long totalProjects = statsService.countProjects();
        return new ResponseEntity<>(totalProjects, HttpStatus.OK);
    }

    @GetMapping("/approved-propositions")
    public ResponseEntity<Long> getApprovedPropositionsStats() {
        long approvedPropositions = statsService.countApprovedPropositions();
        return new ResponseEntity<>(approvedPropositions, HttpStatus.OK);
    }

    @GetMapping("/recent-project-activities")
    public ResponseEntity<List<ProjectActivityDTO>> getRecentProjectActivities() {
        List<ProjectActivityDTO> recentActivities = statsService.getRecentProjectActivities();
        return ResponseEntity.ok(recentActivities);
    }

    @GetMapping("/top-project-owners")
    public ResponseEntity<List<TopProjectOwnerDTO>> getTopProjectOwners() {
        List<TopProjectOwnerDTO> topProjectOwners = statsService.getTopProjectOwners();
        return ResponseEntity.ok(topProjectOwners);
    }

    @GetMapping("/projects-created-over-time")
    public ResponseEntity<List<ProjectCreatedOverTimeDTO>> getProjectsCreatedOverTime() {
        List<ProjectCreatedOverTimeDTO> projectsCreatedOverTime = statsService.getProjectsCreatedOverTime();
        return ResponseEntity.ok(projectsCreatedOverTime);
    }
}
