package tn.esprit.projetPI.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tn.esprit.projetPI.models.Offre;
import tn.esprit.projetPI.repository.OffreRepository;
import tn.esprit.projetPI.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OffreServiceImpl implements OffreService{

    @Autowired
    private OffreRepository offreRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Offre saveOffre(Offre offre) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Extraire l'ID de l'utilisateur connecté
        Long userId;
        if (principal instanceof UserDetailsImpl) {
            userId = ((UserDetailsImpl) principal).getId();
        } else {
            throw new RuntimeException("L'utilisateur authentifié n'est pas trouvé ou n'est pas valide.");
        }
        offre.setUser(userRepository.getOne(userId));
        return offreRepository.save(offre);
    }

    @Override
    public Offre updateOffre(int id, Offre offre) {
        Optional<Offre> optionalOffre = offreRepository.findById(id);
        if (optionalOffre.isPresent()) {
            Offre existingOffre = optionalOffre.get();
            existingOffre.setTitle(offre.getTitle());
            existingOffre.setDescription(offre.getDescription());
            // Mettre à jour d'autres champs si nécessaire
            return offreRepository.save(existingOffre);
        } else {
            throw new RuntimeException("Offre not found with id " + id);
        }
    }

    @Override
    public void deleteOffre(int id) {
        offreRepository.deleteById(id);
    }

    @Override
    public Offre getOffreById(int id) {
        Optional<Offre> optionalOffre = offreRepository.findById(id);
        return optionalOffre.orElse(null);
    }

    @Override
    public List<Offre> getOffreByIduser(int id) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Extraire l'ID de l'utilisateur connecté
        Long userId;
        if (principal instanceof UserDetailsImpl) {
            userId = ((UserDetailsImpl) principal).getId();
        } else {
            throw new RuntimeException("L'utilisateur authentifié n'est pas trouvé ou n'est pas valide.");
        }

        return offreRepository.findByUserId(userId);
    }

    @Override
    public List<Offre> getAllOffres() {
        return offreRepository.findAll();
    }

    @Override
    public List<Offre> getDailyOffers() {
        return offreRepository.findDailyOffers();
    }

    @Override
    public List<Offre> getWeeklyOffers() {
        return offreRepository.findWeeklyOffers();
    }

}
