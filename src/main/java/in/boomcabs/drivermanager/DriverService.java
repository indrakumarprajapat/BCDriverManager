package in.boomcabs.drivermanager;

import java.util.ArrayList;

public interface DriverService {
    ArrayList<Driver> findAllOnlineDrivers();
    void updateBgServiceMqAliveLoction(int driverId,double lat, double lng,int check_alive_counter);
    void updateBgServiceMqAliveLoc_Others(int driverId,int check_alive_counter);
//    void updateBgServiceMqAlive(int driverId);
}