package tn.esprit.projetPI.repository;

import org.springframework.stereotype.Repository;
import tn.esprit.projetPI.models.Offre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface OffreRepository extends JpaRepository<Offre, Integer>{

    List<Offre> findByUserId(Long userId);

}
