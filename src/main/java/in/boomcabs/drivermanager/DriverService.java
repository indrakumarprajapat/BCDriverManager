package in.boomcabs.drivermanager;

import java.util.ArrayList;

public interface DriverService {
    ArrayList<Driver> findAllOnlineDrivers();
    void updateBgServiceMqAliveLoction(int driverId,double lat, double lng);
//    void updateBgServiceMqAlive(int driverId);
}