package tn.esprit.projetPI.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tn.esprit.projetPI.models.Offre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface OffreRepository extends JpaRepository<Offre, Integer>{

    List<Offre> findByUserId(Long userId);

    @Query("SELECT o FROM Offre o WHERE o.createDate = CURRENT_DATE")
    List<Offre> findDailyOffers();

    @Query("SELECT o FROM Offre o WHERE o.createDate >= CURRENT_DATE - 7")
    List<Offre> findWeeklyOffers();

}
