package apps.amaralus.qa.platform;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RedisTransfer implements TransferService {
    @Override
    public void send(OrderI orderI) {
        var m = new ObjectMapper();
    }
}
