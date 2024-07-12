package tn.esprit.projetPI.controllers;

import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.projetPI.models.Proposition;
import tn.esprit.projetPI.repository.PropositionRepository;
import tn.esprit.projetPI.services.QRCodeGeneratorService;

import java.io.IOException;

@RestController
@RequestMapping("/api/qrcode")
public class QRCodeController {

    @Autowired
    private QRCodeGeneratorService qrCodeService;

    @Autowired
    private PropositionRepository propositionRepository;


        @GetMapping("/{propositionId}")
        public ResponseEntity<byte[]> generateQRCode(@PathVariable Long propositionId) {
            // Fetch the proposition and its file path from the database
            Proposition proposition = propositionRepository.findById(propositionId)
                    .orElseThrow(() -> new ResourceNotFoundException("Proposition not found"));
            String filePath = proposition.getFilePath();

            // Ensure the file path does not contain another URL
            if (filePath.contains("://")) {
                filePath = filePath.substring(filePath.lastIndexOf("/") + 1);
            }

            // Construct the Firebase Storage URL
            String fileDownloadUrl = "https://firebasestorage.googleapis.com/v0/b/my-awesome-project-2a31d.appspot.com/o/" + filePath ;

            try {
                byte[] qrCodeImage = qrCodeService.generateQRCodeImage(fileDownloadUrl, 200, 200);
                HttpHeaders headers = new HttpHeaders();
                headers.set(HttpHeaders.CONTENT_TYPE, "image/png");
                return new ResponseEntity<>(qrCodeImage, headers, HttpStatus.OK);
            } catch (WriterException | IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
}