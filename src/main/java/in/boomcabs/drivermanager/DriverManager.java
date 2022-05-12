package in.boomcabs.drivermanager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Timer;

import static in.boomcabs.drivermanager.Constants.TOPIC_DRIVER_LOC_REQ;
import static in.boomcabs.drivermanager.Constants.TOPIC_DRIVER_LOC_REQ_ACK_FULL;

@Component
public class DriverManager {
    private static final Logger LOG = LoggerFactory.getLogger(DriverManager.class);
    @Autowired
    DriverServiceImpl driverService;

    @Autowired
    private MqttPushClient mqttPushClient;

    public DriverManager() {
    }

    //    @Scheduled(fixedRate = 240000)
    @Scheduled(fixedRate = 30000)
    public void checkDriversAndUpdateThem() {
        LOG.info("Average value is {}");
        List<Driver> driverList = driverService.findAllOnlineDrivers();
        mqttPushClient.subscribe(TOPIC_DRIVER_LOC_REQ_ACK_FULL, 1);

        for (int i = 0; i < driverList.size(); i++) {
            Driver driver = driverList.get(i);
            long difference_In_Time
                    = new Date().getTime() - driver.last_loc_update_time.getTime();
            long difference_In_Minutes
                    = (difference_In_Time
                    / (1000 * 60))
                    % 60;
            if (difference_In_Minutes > 4) {
                String payload = driver.driver_id + "|";
                mqttPushClient.publish(1, false, TOPIC_DRIVER_LOC_REQ + driver.driver_id, payload);
            }
        }
    }
}