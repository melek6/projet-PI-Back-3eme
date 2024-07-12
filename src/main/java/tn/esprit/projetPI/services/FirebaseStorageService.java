package tn.esprit.projetPI.services;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;

import com.google.cloud.storage.Blob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
@Service
public class FirebaseStorageService {

    private final Storage storage;
    private final String bucketName;

    @Autowired
    public FirebaseStorageService(Storage storage) {
        this.storage = storage;
        this.bucketName = "my-awesome-project-2a31d.appspot.com"; // Correct bucket name without gs:// prefix
    }

    public String uploadFile(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        BlobId blobId = BlobId.of(bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();
        Blob blob = storage.create(blobInfo, file.getInputStream());
        return blob.getMediaLink();  // Returns the public URL of the uploaded file
    }

    public void deleteFile(String fileName) {
        BlobId blobId = BlobId.of(bucketName, fileName);
        storage.delete(blobId);
    }

    public ByteArrayResource downloadFile(String filePath) throws IOException {
        Blob blob = storage.get(BlobId.of(bucketName, filePath));
        if (blob == null) {
            throw new RuntimeException("File not found in Firebase storage");
        }
        byte[] content = blob.getContent();
        return new ByteArrayResource(content);
    }

    public String getFileDownloadUrl(String filePath) {
        try {
            Blob blob = storage.get(BlobId.of(bucketName, filePath));
            if (blob == null) {
                throw new RuntimeException("File not found in Firebase storage");
            }
            return blob.getMediaLink();
        } catch (Exception e) {
            throw new RuntimeException("Error generating file download URL", e);
        }
    }
}
