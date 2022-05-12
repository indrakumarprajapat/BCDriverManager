package in.boomcabs.drivermanager;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static in.boomcabs.drivermanager.Constants.TOPIC_DRIVER_LOC_REQ_ACK;

/**
 * @Classname PushCallback
 * @Description Consumer monitoring
 * @Date 2019/4/11 23:31
 * @Created by Jack
 */
@Component
public class PushCallback implements MqttCallback {
    private static final Logger logger = LoggerFactory.getLogger(MqttPushClient.class);

    @Autowired
    private MqttConfig mqttConfig;

    @Autowired
    DriverServiceImpl driverService;

    @Override
    public void connectionLost(Throwable throwable) {
        // After the connection is lost, it is usually reconnected here
        logger.info("Disconnected, can be reconnected");
        if (MqttPushClient.client != null) {
            mqttConfig.getMqttPushClient();
        }
    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        // The message you get after you subscribe will be executed here
        String payload = new String(mqttMessage.getPayload());
        logger.info("Receive message subject : " + topic);
        logger.info("receive messages Qos : " + mqttMessage.getQos());
        logger.info("Receive message content : " + payload);
        if (topic.startsWith(TOPIC_DRIVER_LOC_REQ_ACK)) {
            String[] values = payload.split("=");
            int dId = Integer.parseInt(values[0]);
            double lat = values[1].length() == 0 ? 0.0 : Double.parseDouble(values[1]);
            double lng = values[2].length() == 0 ? 0.0 : Double.parseDouble(values[2]);
            driverService.updateBgServiceMqAliveLoction(dId, lat, lng);
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        logger.info("deliveryComplete---------" + iMqttDeliveryToken.isComplete());
    }
}