package tn.esprit.projetPI.repository;

import org.springframework.stereotype.Repository;
import tn.esprit.projetPI.models.Offre;
import org.springframework.data.jpa.repository.JpaRepository;
@Repository
public interface OffreRepository extends JpaRepository<Offre, Integer>{
}
