package tn.esprit.projetPI.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.projetPI.models.InscriptionFormation;
import tn.esprit.projetPI.repository.InscriptionFormationRepository;

import java.util.List;
import java.util.Optional;

@Service
public class InscriptionFormationServiceImp implements InscriptionFormationService {

    private final InscriptionFormationRepository inscriptionFormationRepository;

    @Autowired
    public InscriptionFormationServiceImp(InscriptionFormationRepository inscriptionFormationRepository) {
        this.inscriptionFormationRepository = inscriptionFormationRepository;
    }

    @Override
    public List<InscriptionFormation> retrieveAllInscriptions() {
        return inscriptionFormationRepository.findAll();
    }

    @Override
    public InscriptionFormation addInscription(InscriptionFormation inscriptionFormation) {
        return inscriptionFormationRepository.save(inscriptionFormation);
    }

    @Override
    public InscriptionFormation updateInscription(InscriptionFormation inscriptionFormation) {
        return inscriptionFormationRepository.save(inscriptionFormation);
    }

    @Override
    public Optional<InscriptionFormation> retrieveInscription(int id) {
        return inscriptionFormationRepository.findById(id);
    }

    @Override
    public void deleteInscription(int id) {
        inscriptionFormationRepository.deleteById(id);
    }
}
