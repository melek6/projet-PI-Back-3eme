package tn.esprit.projetPI.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.projetPI.models.Offre;
import tn.esprit.projetPI.repository.OffreRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OffreServiceImpl implements OffreService{

    @Autowired
    private OffreRepository offreRepository;

    @Override
    public Offre saveOffre(Offre offre) {
        return offreRepository.save(offre);
    }

    @Override
    public Offre updateOffre(Offre offre) {
        return offreRepository.save(offre);
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
    public List<Offre> getAllOffres() {
        return offreRepository.findAll();
    }
}
