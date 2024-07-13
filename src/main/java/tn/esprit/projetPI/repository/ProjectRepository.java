package tn.esprit.projetPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tn.esprit.projetPI.dto.ProjectActivityDTO;
import tn.esprit.projetPI.dto.ProjectCreatedOverTimeDTO;
import tn.esprit.projetPI.dto.TopProjectOwnerDTO;
import tn.esprit.projetPI.dto.UserActivityDTO;
import tn.esprit.projetPI.models.Project;
import tn.esprit.projetPI.models.ProjectCategory;
import tn.esprit.projetPI.models.User;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByCategory(ProjectCategory category);
    List<Project> findByCategoryAndBudgetBetween(ProjectCategory category, Double minBudget, Double maxBudget);
    List<Project> findByCategoryAndBudgetGreaterThanEqual(ProjectCategory category, Double minBudget);
    List<Project> findByCategoryAndBudgetLessThanEqual(ProjectCategory category, Double maxBudget);
    List<Project> findByBudgetBetween(Double minBudget, Double maxBudget);
    List<Project> findByBudgetGreaterThanEqual(Double minBudget);
    List<Project> findByBudgetLessThanEqual(Double maxBudget);
    List<Project> findByUser(User user);





    @Query("SELECT new tn.esprit.projetPI.dto.TopProjectOwnerDTO(u.username, COUNT(p), SUM(p.budget)) FROM Project p JOIN p.user u GROUP BY u.username ORDER BY COUNT(p) DESC, SUM(p.budget) DESC")
    List<TopProjectOwnerDTO> findTopProjectOwners();

    @Query("SELECT p.category, COUNT(p) FROM Project p GROUP BY p.category")
    List<Object[]> countProjectsByCategory();
    @Query("SELECT new tn.esprit.projetPI.dto.ProjectActivityDTO('Creation', p.title, p.createdAt) FROM Project p ORDER BY p.createdAt DESC")
    List<ProjectActivityDTO> findRecentProjectActivities();

    @Query(value = "SELECT DATE(p.created_at) as date, COUNT(p.id) as projectCount " +
            "FROM Project p GROUP BY DATE(p.created_at)", nativeQuery = true)
    List<Object[]> findProjectsCreatedOverTimeNative();
}
