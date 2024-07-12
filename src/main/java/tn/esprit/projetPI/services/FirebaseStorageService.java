package tn.esprit.projetPI.services;
import com.google.firebase.cloud.StorageClient;
import com.google.cloud.storage.Blob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FirebaseStorageService {

    private final StorageClient storageClient;

    @Autowired
    public FirebaseStorageService(StorageClient storageClient) {
        this.storageClient = storageClient;
    }

    public String uploadFile(MultipartFile file) throws IOException {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Blob blob = storageClient.bucket().create(fileName, file.getInputStream(), file.getContentType());
        return blob.getMediaLink();  // Returns the public URL of the uploaded file
    }

    public void deleteFile(String fileName) {
        storageClient.bucket().get(fileName).delete();
    }
}
