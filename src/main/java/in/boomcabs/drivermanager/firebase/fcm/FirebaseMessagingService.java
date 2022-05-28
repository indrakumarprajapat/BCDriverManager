package in.boomcabs.drivermanager.firebase.fcm;

import com.google.firebase.messaging.*;
import org.springframework.stereotype.Service;

@Service
public class FirebaseMessagingService {

    private final FirebaseMessaging firebaseMessaging;

    public FirebaseMessagingService(FirebaseMessaging firebaseMessaging) {
        this.firebaseMessaging = firebaseMessaging;
    }


    public String sendNotification(Note note, String token) throws FirebaseMessagingException {
//        Notification notification = Notification
//                .builder()
//                .setTitle(note.getSubject())
//                .setBody(note.getContent())
//                .build();
        WebpushConfig webpushConfig = WebpushConfig.builder().putHeader("Urgency", "high").build();
        AndroidConfig androidConfig = AndroidConfig.builder().setPriority(AndroidConfig.Priority.HIGH)
                .setNotification(AndroidNotification.builder().setChannelId("BOOMCABSOP200219").build()).build();
        Message message = Message
                .builder()
                .setToken(token)
                .setNotification(null)
                .putAllData(note.getData())
                .setWebpushConfig(webpushConfig)
                .setAndroidConfig(androidConfig)
                .build();

        return firebaseMessaging.send(message);
    }

}