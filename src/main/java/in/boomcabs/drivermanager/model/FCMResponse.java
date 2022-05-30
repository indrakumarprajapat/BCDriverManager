package in.boomcabs.drivermanager.model;

import java.util.List;

public class FCMResponse {
    public long multicast_id;
    public int success;
    public int failure;
    public String canonical_ids;
    public List<MessageId> results;
    public static class MessageId{
        public String message_id;
    }
}
