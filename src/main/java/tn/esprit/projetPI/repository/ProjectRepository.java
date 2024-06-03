package tn.esprit.projetPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tn.esprit.projetPI.models.Project;
import tn.esprit.projetPI.models.ProjectCategory;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("SELECT p FROM Project p WHERE (:category IS NULL OR p.category = :category) AND (:skillsRequired IS NULL OR p.skillsRequired LIKE %:skillsRequired%)")
    List<Project> searchProjects(ProjectCategory category, String skillsRequired);
}
