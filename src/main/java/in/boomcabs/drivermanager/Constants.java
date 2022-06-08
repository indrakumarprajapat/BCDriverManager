package in.boomcabs.drivermanager;

public class Constants {
    public static final String ENV_PREFIX = "s";
    public static final String TOPIC_DRIVER_LOC_REQ = ENV_PREFIX + "/drivers/location/req/";
    public static final String TOPIC_DRIVER_LOC_REQ_ACK = ENV_PREFIX + "/drivers/location/req/ack/";
    public static final String TOPIC_DRIVER_LOC_REQ_ACK_FULL = ENV_PREFIX + "/drivers/location/req/ack/+";
    public static final int TIME_PERIOD_LOC_REQUEST = 180;
    public static final int TIME_PERIOD_LOC_REQUEST_UPDATE_BUFFER = 40;
    public static final int CHECK_TIME_PERIOD_LOC_REQUEST = 140;
}
