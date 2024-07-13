package tn.esprit.projetPI.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfigma {

    private final ResourceLoader resourceLoader;

    public FirebaseConfigma(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @PostConstruct
    public void init() throws IOException {
        InputStream serviceAccount = resourceLoader.getResource("classpath:serviceAccountKey.json").getInputStream();
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setStorageBucket("pidev3eme-c5b61.appspot.com")
                .build();

        FirebaseApp.initializeApp(options);
    }
}
