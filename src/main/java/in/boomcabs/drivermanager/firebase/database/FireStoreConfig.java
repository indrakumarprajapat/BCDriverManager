package in.boomcabs.drivermanager.firebase.database;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.firebase.FirebaseApp;
import lombok.var;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class FireStoreConfig {
	@Bean
	public Firestore getFireStore(@Value("${firebase.credential.path}") String credentialPath) throws IOException {
//		var serviceAccount = new FileInputStream(credentialPath);
//		var credentials = GoogleCredentials.fromStream(serviceAccount);
		var options = FirestoreOptions.newBuilder()
						.setCredentials(GoogleCredentials.fromStream(new ClassPathResource(credentialPath).getInputStream())).build();
		return options.getService();
	}
}
