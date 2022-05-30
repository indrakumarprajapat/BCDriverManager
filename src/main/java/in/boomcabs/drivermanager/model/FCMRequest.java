package in.boomcabs.drivermanager.model;

import java.util.ArrayList;

public class FCMRequest {
    public Data data;
    public ArrayList<String> registration_ids = new ArrayList<>();
    public Webpush webpush;
    public Android android;
    public int priority;

    public FCMRequest(String driverId, String token){
        data = new Data(driverId);
        webpush =  new Webpush();
        android = new Android();
        priority = 10;
        registration_ids.add(token);
    }

    public class Android {
        public String priority;
        public Notification notification;
        public Android(){
            priority = "high";
            notification = new Notification();
            notification.channel_id.add("BOOMCABSOP200219");
        }
    }

    public class Notification {
        public ArrayList<String> channel_id = new ArrayList<>();
    }

    public class Webpush {
        public Headers headers;
        public Webpush (){
            headers = new Headers("high");
        }
    }

    public class Headers {
        public String Urgency;
        public Headers(String Urgency){
            this.Urgency = Urgency;
        }
    }

    public class Data {
        public String driverId;
        public Data(String driverId){
            this.driverId = driverId;
        }
    }
}
