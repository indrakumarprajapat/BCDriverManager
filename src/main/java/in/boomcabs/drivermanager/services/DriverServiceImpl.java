package in.boomcabs.drivermanager.services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import in.boomcabs.drivermanager.firebase.database.DriverFirestore;
import in.boomcabs.drivermanager.model.Driver;
import in.boomcabs.drivermanager.repositories.DriverRepository;
import in.boomcabs.drivermanager.services.DriverService;
import in.boomcabs.drivermanager.utilities.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static in.boomcabs.drivermanager.Constants.TIME_PERIOD_LOC_REQUEST;

//  @Service marks a Java class that performs some service,
//  such as executing business logic, performing 
//  calculations, and calling external APIs.
@Service
public class DriverServiceImpl implements DriverService {
    @Autowired
    DriverRepository driverRepository;

    @Override
    public ArrayList<Driver> findAllOnlineDrivers() {
        return (ArrayList<Driver>) driverRepository.findByOnline();
    }

    @Override
    public void updateBgServiceMqAliveLoction(int driverId, double lat, double lng, int check_alive_counter) {
        Date nowTime = DateUtil.getDateTime();
        driverRepository.updateBgServiceMqAliveLoction(true,
                nowTime, true, nowTime, true, nowTime,
                lat, lng, check_alive_counter, driverId);
    }

    @Override
    public void updateAllFirestoreDriverLoc(List<DriverFirestore> drivers) {
        for (int i = 0; i < drivers.size(); i++) {
            DriverFirestore driverFirestore = drivers.get(i);
            Date date = null;
            try {
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                date = format.parse(driverFirestore.datetime);
            } catch (ParseException e) {
                date = DateUtil.getDateTime();
                throw new RuntimeException(e);
            }
            Date nowTime = DateUtil.getDateTime();
            long diffInSeconds = (nowTime.getTime() - date.getTime())/ 1000;
            if (diffInSeconds <= TIME_PERIOD_LOC_REQUEST) {
                driverRepository.updateDriverLocationWithCounter(true,
                        date,
                        driverFirestore.latitude, driverFirestore.longitude, 0 ,driverFirestore.driverId);
            }
        }
    }

    @Override
    public void updateBgServiceMqAliveLoc_Others(int driverId, String username, int check_alive_counter) {
        Date nowTime = DateUtil.getDateTime();
        if (check_alive_counter == 0) {
            check_alive_counter += 1;
            driverRepository.updateCounter(nowTime, check_alive_counter, driverId);
        } else if (check_alive_counter == 1) {
            check_alive_counter += 1;
            driverRepository.updateCounterLocFlagLocTime(nowTime, check_alive_counter, false, nowTime, driverId);
        } else if (check_alive_counter == 2 || check_alive_counter == 3) {
            check_alive_counter += 1;
            driverRepository.updateCounterLocFlag(nowTime, check_alive_counter, false, driverId);
        } else if (check_alive_counter >= 4) {
            check_alive_counter += 1;
            driverRepository.updateBgServiceMqAliveLoctionLastFailed(nowTime, 1, false,
                    nowTime, false, nowTime, false, nowTime, check_alive_counter, driverId);
//            SendSMS.sendSms("Hello! Your Boom Partner app was inactive for a long time, so we have made you offline. Please go online to accept new rides.",username);
//            SendSMS.sendSms("வணக்கம்! உங்கள் பூம் பார்ட்னர் ஆப்ஸ் நீண்ட நேரம் செயல்படாமல் இருந்ததால், உங்களை ஆஃப்லைனில் மாற்றியுள்ளோம். புதிய சவாரிகளை ஏற்க மீண்டும் ஆன்லைனுக்கு செல்லவும்.",username);
        }
    }
}