package in.boomcabs.drivermanager;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class Driver {
    @Id
    public int driver_id;
    public int operators_id_fk;
    public String first_name;
    public String last_name;
    public String username;
    public String password;
    public String email;
//    public String verification_token;
    public String full_name;
    public String new_mobile_no;
    public String profile_mob_otp;
    public String register_mob_otp;
    public String profile_id_image;
    public Integer otp;
    public Integer is_account_verified;
    public int status;
    public String ios_push_token;
    public String android_push_token;
    public Boolean is_loc_updated;
    public double lng;
    public double lat;
    public Date last_loc_update_time;
    public Integer account_status;
    public Integer is_outerstation_rides;
    public Integer docs_verified;
    public String permanent_address;
    public String date_of_birth;
    public String blood_group;
    public Integer user_type;
    public String city_code;
    public String token;
    public Integer is_operator_driving;
    public Integer license_front;
    public Integer license_back;
    public Integer aadhar_front;
    public Integer aadhar_back;
    public String referral_code;
    public Integer referral_count;
    public Date created_date;
    public String app_cur_version;
    public Integer d_os_api;
    public String d_manufacture;
    public String d_model;
    public String d_os_version;
    public Integer agent_id_fk;
    public Integer is_website_reg;
    public String driver_license_id;
    public Integer login_device;
//    public int city_zone_id;
//    public String cab_driveruserscol;
    public Boolean is_mq_alive;
    public Date is_mq_alive_time;
    public Boolean is_bgservice_alive;
    public Date is_bgservice_alive_time;
    public Boolean  is_bgservice_stop_manual;
    public Date last_login_time;
    public Date last_autologin_time;
    public Date last_minimized_time;
    public Boolean is_bat_opt_disable;
    public Boolean is_manuf_bat_opt_disable;
    public Date last_closed_time;
    public int check_alive_counter;
//    public boolean  is_app_uninstalled;
}
