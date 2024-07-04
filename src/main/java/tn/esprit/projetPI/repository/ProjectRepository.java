package tn.esprit.projetPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
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
}
