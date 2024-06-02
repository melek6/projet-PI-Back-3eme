package tn.esprit.projetPI.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.projetPI.models.Reponse;
import tn.esprit.projetPI.repository.ReponseRepository;

import java.util.List;
import java.util.Optional;

    @Service
    public class ReponseImpl {
        @Autowired
        private ReponseRepository reponseRepository;

        public Reponse saveOrUpdateReponse(Reponse reponse) {
            return reponseRepository.save(reponse);
        }

        public Optional<Reponse> getReponseById(Integer id) {
            return reponseRepository.findById(id);
        }

        public List<Reponse> getAllReponses() {
            return reponseRepository.findAll();
        }

        public void deleteReponseById(Integer id) {
            reponseRepository.deleteById(id);
        }
    }


