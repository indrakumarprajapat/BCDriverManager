package in.boomcabs.drivermanager.firebase.fcm;

import com.google.firebase.messaging.*;
import in.boomcabs.drivermanager.model.FCMRequest;
import in.boomcabs.drivermanager.model.FCMResponse;
import in.boomcabs.drivermanager.model.PushyRequest;
import in.boomcabs.drivermanager.model.PushyResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.HashMap;
import java.util.Map;

@Service
public class FirebaseMessagingService {

    private final FirebaseMessaging firebaseMessaging;

    public FirebaseMessagingService(FirebaseMessaging firebaseMessaging) {
        this.firebaseMessaging = firebaseMessaging;
    }

    @Bean()
    public CloseableHttpClient httpClient() {
        return HttpClientBuilder.create().build();
    }

    @Autowired
    CloseableHttpClient httpClient;

    @Value("${fcm.api.host.baseurl}")
    private String apiHost;
    @Value("${pushy.api.host.baseurl}")
    private String pushyApiHost;

    private final String FCM_SEND = "/fcm/send";
    private final String PUSHY_SEND = "/push?api_key=b0d1dd46b88e14eeb1f1929e645144dce945f1eb6047a19afa031dce3f1573fe";

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory());
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(apiHost));
        return restTemplate;
    }

    @Bean
    @ConditionalOnMissingBean
    public HttpComponentsClientHttpRequestFactory clientHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory
                = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setHttpClient(httpClient);
        return clientHttpRequestFactory;
    }

    @Autowired
    RestTemplate restTemplate;

    public void sendNotificationAPI(int dId, String token) throws FirebaseMessagingException {
        FCMRequest fcmRequest = new FCMRequest(String.valueOf(dId),token);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "key=AAAA9PvZNuI:APA91bG_M-HbuFBFv6ck0dvh6ZB1qnZ_JR4TTrywAzEqa1hbKqNvsv71KCFlKUa1rUHvyp0gNR5Ircm0kDeO_ICZwpfeAtwMJbr6lk3Ftbefgf_wO-S46jcDriYJ1dt1LzCp5iZCs8ns");
            HttpEntity request = new HttpEntity(fcmRequest,headers);
            ResponseEntity<FCMResponse> response = new RestTemplate().exchange(apiHost+FCM_SEND, HttpMethod.POST, request, FCMResponse.class);
            FCMResponse responseVal = response.getBody();
            System.out.println(responseVal.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void sendPushiNotificationAPI(int dId, String pushyToken) throws FirebaseMessagingException {
        PushyRequest pushyRequest = new PushyRequest(String.valueOf(dId),pushyToken,"","");
        try {
            HttpHeaders headers = new HttpHeaders();
//            headers.add("Authorization", "key=AAAA9PvZNuI:APA91bG_M-HbuFBFv6ck0dvh6ZB1qnZ_JR4TTrywAzEqa1hbKqNvsv71KCFlKUa1rUHvyp0gNR5Ircm0kDeO_ICZwpfeAtwMJbr6lk3Ftbefgf_wO-S46jcDriYJ1dt1LzCp5iZCs8ns");
            HttpEntity request = new HttpEntity(pushyRequest,headers);
            ResponseEntity<PushyResponse> response = new RestTemplate().exchange(pushyApiHost+PUSHY_SEND, HttpMethod.POST, request, PushyResponse.class);
            PushyResponse responseVal = response.getBody();
            System.out.println(responseVal.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String sendNotification(int dId, String token) throws FirebaseMessagingException {
//        Note note = new Note();
//        note.setSubject("BoomCab");
//        note.setContent("Track Location");
//        Map<String, String> data = new HashMap<String, String>();
//        data.put("title","boomcab");
//        data.put("body","Track Location");
//        note.setData(data);
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
                .putData("driverId",String.valueOf(dId))
//                .setWebpushConfig(webpushConfig)
//                .setAndroidConfig(androidConfig)
                .setToken(token)
//                .setNotification(null)
                .build();

        return firebaseMessaging.send(message);
    }

}