package tn.esprit.projetPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.projetPI.models.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}
