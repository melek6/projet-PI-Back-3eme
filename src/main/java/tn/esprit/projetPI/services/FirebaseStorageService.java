package tn.esprit.projetPI.services;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class FirebaseStorageService implements FirebaseStorageServiceipm {

    private final Storage storage;
    private final String bucketName;

    @Autowired
    public FirebaseStorageService(ResourceLoader resourceLoader) throws IOException {
        InputStream serviceAccount = resourceLoader.getResource("classpath:serviceAccountKey.json").getInputStream();
        GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
        this.storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        this.bucketName = "pidev3eme-c5b61.appspot.com";
    }

    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
        BlobId blobId = BlobId.of(bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();
        storage.create(blobInfo, file.getBytes());

        return "https://firebasestorage.googleapis.com/v0/b/" + bucketName + "/o/" + fileName + "?alt=media";
    }
}
