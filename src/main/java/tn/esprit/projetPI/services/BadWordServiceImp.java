package tn.esprit.projetPI.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.projetPI.models.BadWord;
import tn.esprit.projetPI.repository.BadWordRepository;

import java.util.List;

@Service
public class BadWordServiceImp   implements IbadWordService{

    @Autowired
    private BadWordRepository badWordRepository;

    public boolean containsBadWord(String text) {
        List<BadWord> badWords = badWordRepository.findAll();
        for (BadWord badWord : badWords) {
            if (text.toLowerCase().contains(badWord.getWord().toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}
