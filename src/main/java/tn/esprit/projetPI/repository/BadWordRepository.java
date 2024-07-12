package tn.esprit.projetPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.projetPI.models.BadWord;

public interface BadWordRepository extends JpaRepository<BadWord, Long> {
}