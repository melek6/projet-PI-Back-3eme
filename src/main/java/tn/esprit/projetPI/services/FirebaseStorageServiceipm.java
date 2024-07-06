package tn.esprit.projetPI.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FirebaseStorageServiceipm {
    public String uploadFile(MultipartFile file) throws IOException;
}
