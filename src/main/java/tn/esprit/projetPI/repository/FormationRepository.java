package tn.esprit.projetPI.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.projetPI.models.Formation;

import java.util.List;
import java.util.Optional;

@Repository
public interface FormationRepository extends JpaRepository<Formation, Integer> {
    List<Formation> findByBestSeller(boolean bestSeller);
    List<Formation> findByNewFormation(boolean newFormation);
}
