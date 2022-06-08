package in.boomcabs.drivermanager.model;

import java.util.ArrayList;

public class PushyRequest {
    public String to;
    public Data data;
    public Notification notification;

    public PushyRequest(String driverId, String token,String title, String body){
        to = token;
        data = new Data();
        data.driverid = driverId;
        data.message = driverId;
        notification = new Notification();
        notification.title = title;
        notification.body = body;
    }

    public class Notification {
        public String title;
        public String body;
    }

    public class Data {
        public String message;
        public String driverid;
    }
}
