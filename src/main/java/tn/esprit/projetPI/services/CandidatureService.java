package tn.esprit.projetPI.services;

import tn.esprit.projetPI.models.Candidature;

import java.util.List;
public interface CandidatureService {
    Candidature saveCandidature(Candidature candidature);
    Candidature updateCandidature(Candidature candidature);
    void deleteCandidature(int id);
    Candidature getCandidatureById(int id);
    List<Candidature> getAllCandidatures();
}
