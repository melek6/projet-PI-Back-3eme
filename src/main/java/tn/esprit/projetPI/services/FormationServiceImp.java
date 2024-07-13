package tn.esprit.projetPI.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.projetPI.controllers.ResourceNotFoundException;
import tn.esprit.projetPI.models.Evaluation;
import tn.esprit.projetPI.models.Formation;
import tn.esprit.projetPI.models.FormationCategory;
import tn.esprit.projetPI.models.InscriptionFormation;
import tn.esprit.projetPI.repository.EvaluationRepository;
import tn.esprit.projetPI.repository.FormationRepository;
import tn.esprit.projetPI.repository.InscriptionFormationRepository;
import tn.esprit.projetPI.repository.UserRepository;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FormationServiceImp implements FormationService {
    @Autowired
    private FormationRepository formationRepository;
    @Autowired
    private EvaluationRepository evaluationRepository;
    @Value("${file.upload-dir}")
    private String uploadDir;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private InscriptionFormationRepository inscriptionFormationRepository;

    @Override
    public List<Formation> retrieveAllFormations() {
        return formationRepository.findAll();
    }

    @Override
    public Formation addFormation(Formation formation) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Long userId;
        if (principal instanceof UserDetailsImpl) {
            userId = ((UserDetailsImpl) principal).getId();
        } else {
            throw new RuntimeException("L'utilisateur authentifié n'est pas trouvé ou n'est pas valide.");
        }

        formation.setUser(userRepository.getOne(userId));
        return formationRepository.save(formation);
    }

    @Override
    public Formation addFormationByCategory(Formation formation, FormationCategory category) {
        formation.setCategory(category);
        return addFormation(formation);
    }

    @Override
    public Formation updateFormation(Formation formation) {
        return formationRepository.save(formation);
    }

    @Override
    public Optional<Formation> retrieveFormation(int id) {
        return formationRepository.findById(id);
    }

    @Override
    public void deleteFormation(int id) {
        formationRepository.deleteById(id);
    }

    @Override
    public Evaluation addEvaluationToFormation(int formationId, Evaluation evaluation) throws ResourceNotFoundException {
        Optional<Formation> formationOptional = formationRepository.findById(formationId);
        if (!formationOptional.isPresent()) {
            throw new ResourceNotFoundException("Formation not found with id: " + formationId);
        }
        Formation formation = formationOptional.get();
        evaluation.setFormation(formation);
        return evaluationRepository.save(evaluation);
    }


    @Override
    public List<Evaluation> getEvaluationsForFormation(int formationId) {
        Optional<Formation> optionalFormation = formationRepository.findById(formationId);
        if (optionalFormation.isPresent()) {
            Formation formation = optionalFormation.get();
            return formation.getEvaluations();
        } else {
            throw new ResourceNotFoundException("Formation not found with id: " + formationId);
        }
    }

    @Override
    public List<Formation> getBestSellerFormations() {
        return formationRepository.findByBestSeller(true);
    }

    public List<Formation> getCompletedFormations(Long userId) {
        return formationRepository.findCompletedFormationsByUserId(userId);
    }

    @Override
    public List<Formation> getFormationsByCategory(FormationCategory category) {
        return formationRepository.findByCategory(category);
    }

    public List<Formation> getCompletedFormationsByUser(Long userId) {
        Date now = new Date();
        List<InscriptionFormation> inscriptions = inscriptionFormationRepository.findByUserIdAndFormationEndDateBefore(userId, now);
        return inscriptions.stream().map(InscriptionFormation::getFormation).collect(Collectors.toList());
    }

    @Override
    public String uploadPlanningFile(int formationId, MultipartFile file) {
        if (file.isEmpty()) {
            throw new RuntimeException("Veuillez sélectionner un fichier à télécharger");
        }

        try {
            // Créer le répertoire s'il n'existe pas
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Sauvegarder le fichier
            String fileName = file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            file.transferTo(filePath.toFile());

            // Mettre à jour l'entité Formation avec le chemin du fichier
            Formation formation = retrieveFormation(formationId).orElseThrow(() -> new ResourceNotFoundException("Formation non trouvée"));
            formation.setPlanning(filePath.toString());
            formationRepository.save(formation);

            return "Fichier téléchargé avec succès";
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors du téléchargement du fichier", e);
        }
    }

    @Override
    public List<Formation> getRecommendedFormations() {
        return formationRepository.findTopFormationsByAverageScore().subList(0, 5);  // Récupère les 5 meilleures formations
    }

    @Transactional
    public void updatePlanningUrl(int formationId, String planningUrl) {
        Formation formation = formationRepository.findById(formationId)
                .orElseThrow(() -> new RuntimeException("Formation not found with id: " + formationId));
        formation.setPlanningUrl(planningUrl);
        formationRepository.save(formation);
    }


}
