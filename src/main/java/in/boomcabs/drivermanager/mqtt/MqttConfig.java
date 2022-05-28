package in.boomcabs.drivermanager.mqtt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

import static in.boomcabs.drivermanager.Constants.TOPIC_DRIVER_LOC_REQ_ACK_FULL;

/**
 * @Classname MqttConfig
 * @Description mqtt Related configuration information
 * @Date 2020/3/5 11:00
 * @Created by bam
 */
@Component
@ConfigurationProperties("mqtt")
@Setter
@Getter
public class MqttConfig {
    @Autowired
    private MqttPushClient mqttPushClient;

    /**
     * User name
     */
   // @Value("username")
    private String username;
    /**
     * Password
     */
    private String password;
    /**
     * Connection address
     */
    private String hostUrl;
    /**
     * Customer Id
     */
    private String clientID;
    /**
     * Default connection topic
     */
    private String defaultTopic;
    /**
     * Timeout time
     */
    private int timeout;
    /**
     * Keep connected
     */
    private int keepalive;

    @Bean
    public MqttPushClient getMqttPushClient() {
    	System.out.println("hostUrl: "+ hostUrl);
    	System.out.println("clientID: "+ clientID);
    	System.out.println("username: "+ username);
    	System.out.println("password: "+ password);
    	System.out.println("timeout: "+timeout);
    	System.out.println("keepalive: "+ keepalive);
        mqttPushClient.connect(hostUrl, clientID, username, password, timeout, keepalive);
        // End with / / to subscribe to all topics starting with test
//        mqttPushClient.subscribe("s/rd/handshake/ack/#", 1);
        mqttPushClient.subscribe(TOPIC_DRIVER_LOC_REQ_ACK_FULL, 1);
        return mqttPushClient;
    }
}
