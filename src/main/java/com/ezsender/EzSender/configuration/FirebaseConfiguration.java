package com.ezsender.EzSender.configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@Slf4j
@Configuration
public class FirebaseConfiguration {

    @Value("${ezsender.fcm.key.store.path}")
    private String fcmKey;

    @PostConstruct
    public void init() {
        try (var inputStream = new ClassPathResource(fcmKey).getInputStream()) {
            var options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(inputStream))
                    .build();
            FirebaseApp.initializeApp(options);
            log.info("Firebase :::::::: Initialization Done.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
