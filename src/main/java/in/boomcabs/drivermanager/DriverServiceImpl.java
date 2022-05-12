package in.boomcabs.drivermanager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void updateBgServiceMqAliveLoction(int driverId,double lat, double lng) {
        String nowTimeStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        List<Driver> driverOpt = driverRepository.findByPrimaryId(driverId);
        if(driverOpt.size() > 0){
            Driver driver = driverOpt.get(0);
            driver.is_mq_alive = true;
            driver.is_bgservice_alive = true;
            driver.is_loc_updated = true;
            driver.is_mq_alive_time = new Date();
            driver.is_bgservice_alive_time = new Date();
            driver.last_loc_update_time = new Date();
            driver.lat = lat;
            driver.lng = lng;
//            driverRepository.save(driver);
            driverRepository.updateBgServiceMqAliveLoction(driver.is_loc_updated,driver.driver_id);
        }
//        driverRepository.updateBgServiceMqAliveLoction(1, nowTimeStr, 1, nowTimeStr, 1
//, nowTimeStr, lat, lng, driverId);
    }

//    @Override
//    public void updateBgServiceMqAlive(int driverId) {
//        String nowTimeStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
////        driverRepository.updateBgServiceMqAlive(1, nowTimeStr, 1, nowTimeStr, 1
////                ,driverId);
//    }
}