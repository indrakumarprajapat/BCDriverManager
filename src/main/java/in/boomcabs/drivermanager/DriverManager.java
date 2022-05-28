package in.boomcabs.drivermanager;

import in.boomcabs.drivermanager.firebase.database.DriverFirestore;
import in.boomcabs.drivermanager.firebase.fcm.FirebaseMessagingService;
import in.boomcabs.drivermanager.firebase.fcm.Note;
import in.boomcabs.drivermanager.model.Driver;
import in.boomcabs.drivermanager.mqtt.MqttPushClient;
import in.boomcabs.drivermanager.firebase.database.DriversRepository;
import in.boomcabs.drivermanager.services.DriverServiceImpl;
import in.boomcabs.drivermanager.utilities.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

import static in.boomcabs.drivermanager.Constants.*;

@Component
public class DriverManager {
    private static final Logger LOG = LoggerFactory.getLogger(DriverManager.class);
    @Autowired
    DriverServiceImpl driverService;

    @Autowired
    private MqttPushClient mqttPushClient;

    private final FirebaseMessagingService firebaseService;

    DriversRepository driversRepository;

    public DriverManager(FirebaseMessagingService firebaseService,DriversRepository driversRepository ) {
        this.firebaseService = firebaseService;
        this.driversRepository = driversRepository;
    }

    //                        DriverFirestore driverFirestore = driversRepository.getDriver(driver.driver_id);
//                        if(driverFirestore != null){
//                            LOG.debug("DID-> " + driverFirestore.driverId + "-"+driverFirestore.latitude + "-"+driverFirestore.longitude);
//                        }

    //    @Scheduled(fixedRate = 240000)
    @Scheduled(fixedRate = (TIME_PERIOD_LOC_REQUEST*1000))
    public void checkDriversAndUpdateThem() {
        List<Driver> driverList = driverService.findAllOnlineDrivers();
        mqttPushClient.subscribe(TOPIC_DRIVER_LOC_REQ_ACK_FULL, 1);

        List<DriverFirestore> driversFirestore = driversRepository.retrieveAll();
        driverService.updateAllFirestoreDriverLoc(driversFirestore);

        for (int i = 0; i < driverList.size(); i++) {
            Driver driver = driverList.get(i);
            try {
                //Sending check loc msg
                Date nowTime = DateUtil.getDateTime();
                long diffInSeconds = (nowTime.getTime() - driver.last_loc_update_time.getTime())/ 1000;
                if (diffInSeconds >= CHECK_TIME_PERIOD_LOC_REQUEST) {
                    String payload = driver.driver_id + "|";
//                    //commenting to tempaoroarly stop mq loc request
//                    mqttPushClient.publish(1, false, TOPIC_DRIVER_LOC_REQ + driver.driver_id, payload);
                    //
//                    if(driver.username.equals("7094088388") || drive  r.username.equals("8015069817")
//                            || driver.username.equals("7094099399")){
                        Note note = new Note();
                        note.setSubject("BoomCab");
                        note.setContent("Track Location");
                        Map<String, String> data = new HashMap<String, String>();
                        data.put("title","boomcab");
                        data.put("body","Track Location");
                        note.setData(data);
                        firebaseService.sendNotification(note,driver.android_push_token);
//                    }
//                    new ResponseEntity<>(new PushNotificationResponse(HttpStatus.OK.value(), "Notification has been sent."), HttpStatus.OK);
                }
                //updating values
                try {
                    driverService.updateBgServiceMqAliveLoc_Others(driver.driver_id,driver.username,driver.check_alive_counter);
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}