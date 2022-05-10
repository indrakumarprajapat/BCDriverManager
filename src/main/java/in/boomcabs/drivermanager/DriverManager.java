package in.boomcabs.drivermanager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DriverManager {
    private static final Logger LOG = LoggerFactory.getLogger(DriverManager.class);

    public DriverManager() {
    }

    @Scheduled(fixedRate = 240000)
    public void checkDriversAndUpdateThem() {
        LOG.info("Average value is {}");

    }
}