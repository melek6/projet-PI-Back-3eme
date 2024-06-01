package tn.esprit.projetPI.services;
import tn.esprit.projetPI.models.Offre;
import java.util.List;
public interface OffreService {
    Offre saveOffre(Offre offre);
    Offre updateOffre(Offre offre);
    void deleteOffre(int id);
    Offre getOffreById(int id);
    List<Offre> getAllOffres();
}
