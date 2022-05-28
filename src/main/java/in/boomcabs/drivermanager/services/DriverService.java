package in.boomcabs.drivermanager.services;

import in.boomcabs.drivermanager.firebase.database.DriverFirestore;
import in.boomcabs.drivermanager.model.Driver;

import java.util.ArrayList;
import java.util.List;

public interface DriverService {
    ArrayList<Driver> findAllOnlineDrivers();
    void updateBgServiceMqAliveLoction(int driverId,double lat, double lng,int check_alive_counter);
    void updateBgServiceMqAliveLoc_Others(int driverId,String username,int check_alive_counter);
    public void updateAllFirestoreDriverLoc(List<DriverFirestore> drivers) ;
}