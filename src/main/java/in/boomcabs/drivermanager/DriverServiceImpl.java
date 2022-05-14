package in.boomcabs.drivermanager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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
    public void updateBgServiceMqAliveLoction(int driverId, double lat, double lng,int check_alive_counter) {
        Date nowTime = DateUtil.getDateTime();
        driverRepository.updateBgServiceMqAliveLoction(true,
                nowTime, true, nowTime, true, nowTime,
                lat, lng,check_alive_counter ,driverId);
    }

    @Override
    public void updateBgServiceMqAliveLoc_Others(int driverId, int check_alive_counter) {
        Date nowTime = DateUtil.getDateTime();
        if(check_alive_counter == 0){
            check_alive_counter += 1;
            driverRepository.updateCounter(nowTime, check_alive_counter,driverId);
        }else if(check_alive_counter == 1) {
            check_alive_counter += 1;
            driverRepository.updateCounterLocFlagLocTime(nowTime, check_alive_counter, false, nowTime, driverId);
        }else if(check_alive_counter == 2 || check_alive_counter == 3) {
            check_alive_counter += 1;
            driverRepository.updateCounterLocFlag(nowTime, check_alive_counter, false,driverId);
        }else if(check_alive_counter == 4){
            check_alive_counter += 1;
            driverRepository.updateBgServiceMqAliveLoctionLastFailed(nowTime, 0,false,
                    nowTime, false, nowTime, false, nowTime,check_alive_counter ,driverId);
        }
    }
}