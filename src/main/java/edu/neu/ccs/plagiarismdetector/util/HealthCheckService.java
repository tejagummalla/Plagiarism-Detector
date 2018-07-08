package edu.neu.ccs.plagiarismdetector.util;

import it.zielke.moji.SocketClient;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Service;

/**
 * Custom Health Check Service to check the health of the system
 */
@Service
public class HealthCheckService implements HealthIndicator {

    /**
     * To chec k the health status of the system
     * @return the health status of the system
     */
    @Override
    public Health health() {
        int errorCode = check();
        if (errorCode != 200) {
            return Health.down()
                    .withDetail("Error Code", errorCode).build();
        }
        return Health.up().build();
    }

    /**
     * Check the amount of disk space that is left and utilized
     * @return the cod eif system is up or not.
     */
    private int check() {
        String userId = null;
        try {
            SocketClient socketClient = new SocketClient();
            socketClient.setUserID("747158892");
            socketClient.setLanguage("c");
            userId = socketClient.getUserID(); // Check if MOSS is up
        } catch (Exception e) {
            return 404;
        }
        if (userId != null)
            return 200;
        else
            return 404;
    }


}
