package in.boomcabs.drivermanager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Integer> {
    @Query(value = "SELECT * FROM cab_driverusers cd WHERE cd.status = 1", nativeQuery = true)
    List<Driver> findByOnline();

    @Query(value = "SELECT * FROM cab_driverusers cd WHERE cd.driver_id = :driver_id", nativeQuery = true)
    List<Driver> findByPrimaryId(@Param("driver_id") int driver_id);

    @Transactional
    @Modifying
    @Query("UPDATE Driver SET is_bgservice_alive = :is_bgservice_alive, is_bgservice_alive_time = :is_bgservice_alive_time, is_mq_alive = :is_mq_alive, is_mq_alive_time = :is_mq_alive_time, is_loc_updated = :is_loc_updated, last_loc_update_time = :last_loc_update_time, lat = :lat, lng = :lng, check_alive_counter = :check_alive_counter WHERE driver_id = :driver_id")
    void updateBgServiceMqAliveLoction(@Param(value = "is_bgservice_alive") boolean is_bgservice_alive, @Param(value = "is_bgservice_alive_time") Date is_bgservice_alive_time,
                                       @Param(value = "is_mq_alive") boolean is_mq_alive, @Param(value = "is_mq_alive_time") Date is_mq_alive_time,
                                       @Param(value = "is_loc_updated") boolean is_loc_updated,@Param(value = "last_loc_update_time") Date last_loc_update_time,
                                       @Param(value = "lat") double lat, @Param(value = "lng") double lng,
                                       @Param(value = "check_alive_counter") int check_alive_counter, @Param(value = "driver_id") int driver_id);

    @Transactional
    @Modifying
    @Query("UPDATE Driver SET status = :status, is_bgservice_alive = :is_bgservice_alive, is_bgservice_alive_time = :is_bgservice_alive_time, is_mq_alive = :is_mq_alive, is_mq_alive_time = :is_mq_alive_time, is_loc_updated = :is_loc_updated, last_loc_update_time = :last_loc_update_time, check_alive_counter = :check_alive_counter WHERE driver_id = :driver_id")
    void updateBgServiceMqAliveLoctionLastFailed(@Param(value = "status") int status, @Param(value = "is_bgservice_alive") boolean is_bgservice_alive, @Param(value = "is_bgservice_alive_time") Date is_bgservice_alive_time,
                                       @Param(value = "is_mq_alive") boolean is_mq_alive, @Param(value = "is_mq_alive_time") Date is_mq_alive_time,
                                       @Param(value = "is_loc_updated") boolean is_loc_updated,@Param(value = "last_loc_update_time") Date last_loc_update_time,
                                       @Param(value = "check_alive_counter") int check_alive_counter, @Param(value = "driver_id") int driver_id);

    @Transactional
    @Modifying
    @Query("UPDATE Driver SET check_alive_counter = :check_alive_counter WHERE driver_id = :driver_id")
    void updateCounter( @Param(value = "check_alive_counter") int check_alive_counter,
                        @Param(value = "driver_id") int driver_id);

    @Transactional
    @Modifying
    @Query("UPDATE Driver SET check_alive_counter = :check_alive_counter,is_loc_updated = :is_loc_updated WHERE driver_id = :driver_id")
    void updateCounterLocFlag( @Param(value = "check_alive_counter") int check_alive_counter,
                               @Param(value = "is_loc_updated") boolean is_loc_updated,
                               @Param(value = "driver_id") int driver_id);

    @Transactional
    @Modifying
    @Query("UPDATE Driver SET check_alive_counter = :check_alive_counter,is_loc_updated = :is_loc_updated, last_loc_update_time = :last_loc_update_time WHERE driver_id = :driver_id")
    void updateCounterLocFlagLocTime(@Param(value = "check_alive_counter") int check_alive_counter,
                                     @Param(value = "is_loc_updated") boolean is_loc_updated,
                                     @Param(value = "last_loc_update_time") Date last_loc_update_time,
                                     @Param(value = "driver_id") int driver_id);


//    @Modifying
//    @Query("UPDATE Driver cd SET cd.is_loc_updated = :is_loc_updated WHERE cd.driver_id = :driver_id")
//    int updateBgServiceMqAliveLoction(@Param(value = "is_loc_updated") boolean is_loc_updated
//            ,@Param(value = "driver_id") int driver_id);

//    @Modifying
//    @Query("UPDATE cab_driverusers SET is_bgservice_alive = :is_bgservice_alive, is_bgservice_alive_time = :is_bgservice_alive_time, " +
//            "is_mq_alive = :is_mq_alive, is_mq_alive_time = :is_mq_alive_time, is_loc_updated = :is_loc_updated,  last_loc_update_time = :last_loc_update_time, " +
//            "lat = :lat, lng = :lng WHERE driver_id = :driver_id")
//    void updateBgServiceMqAliveLoction(@Param(value = "is_bgservice_alive") int is_bgservice_alive, @Param(value = "is_bgservice_alive_time") String is_bgservice_alive_time,
//                     @Param(value = "is_mq_alive") int is_mq_alive,@Param(value = "is_mq_alive_time") String is_mq_alive_time,
//                     @Param(value = "is_loc_updated") int is_loc_updated, @Param(value = "last_loc_update_time") String last_loc_update_time,
//            @Param(value = "lat") double lat, @Param(value = "lng") double lng,@Param(value = "driver_id") int driver_id);

//    @Query(value = "SELECT * from Employee e where e.employeeName =:name AND e.employeeRole = :role ", nativeQuery = true)
//    List<Driver> findByNameAndRoleNative(@Param("name") String name, @Param("role")String role);

//    void updateBgServiceMqAliveLoction(int is_bgservice_alive, String is_bgservice_alive_time,
//                                        int is_mq_alive, String is_mq_alive_time,
//                                       int is_loc_updated, String last_loc_update_time,
//                                       double lat, double lng, int driver_id);
//    @Modifying
//    @Query("UPDATE cab_driverusers SET is_bgservice_alive = :is_bgservice_alive, is_bgservice_alive_time = :is_bgservice_alive_time, " +
//            "is_mq_alive = :is_mq_alive, is_mq_alive_time = :is_mq_alive_time, is_loc_updated = :is_loc_updated,  last_loc_update_time = :last_loc_update_time, " +
//            "lat = :lat, lng = :lng WHERE driver_id = :driver_id")
//    void updateBgServiceMqAliveLoction(@Param(value = "is_bgservice_alive") int is_bgservice_alive, @Param(value = "is_bgservice_alive_time") String is_bgservice_alive_time,
//                     @Param(value = "is_mq_alive") int is_mq_alive,@Param(value = "is_mq_alive_time") String is_mq_alive_time,
//                     @Param(value = "is_loc_updated") int is_loc_updated, @Param(value = "last_loc_update_time") String last_loc_update_time,
//            @Param(value = "lat") double lat, @Param(value = "lng") double lng,@Param(value = "driver_id") int driver_id);
//
//    @Modifying
//    @Query("UPDATE cab_driverusers cd SET cd.is_bgservice_alive = :is_bgservice_alive, cd.is_bgservice_alive_time = :is_bgservice_alive_time, " +
//            "cd.is_mq_alive = :is_mq_alive, cd.is_mq_alive_time = :is_mq_alive_time, cd.is_loc_updated = :is_loc_updated WHERE cd.driver_id = :driver_id")
//    void updateBgServiceMqAlive(@Param(value = "is_bgservice_alive") int is_bgservice_alive, @Param(value = "is_bgservice_alive_time") String is_bgservice_alive_time,
//                                   @Param(value = "is_mq_alive") int is_mq_alive,@Param(value = "is_mq_alive_time") String is_mq_alive_time,
//                                   @Param(value = "is_loc_updated") int is_loc_updated, @Param(value = "driver_id") int driver_id);
}