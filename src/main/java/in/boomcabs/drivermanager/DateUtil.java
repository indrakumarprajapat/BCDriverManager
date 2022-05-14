package in.boomcabs.drivermanager;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static Date getDateTime(){
        Calendar cal = Calendar.getInstance(); // creates calendar
        cal.setTime(new Date());               // sets calendar time/date
        cal.add(Calendar.HOUR_OF_DAY, 5);      // adds one hour
        cal.add(Calendar.MINUTE, 30);      // adds one hour
        return  cal.getTime();                         // returns new date object plus one hour
    }
}
