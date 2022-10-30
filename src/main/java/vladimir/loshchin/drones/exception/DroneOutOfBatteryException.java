package vladimir.loshchin.drones.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import vladimir.loshchin.drones.model.Drone;

public class DroneOutOfBatteryException  extends ResponseStatusException {

    public DroneOutOfBatteryException(Drone drone, double threshold) {
        super(HttpStatus.CONFLICT, String.format(
                  "Drone %s out of battery. Current battery charge %.2f. Threshold: %.2f.",
                  drone.getSerial(), drone.getBatteryCharge(), threshold));
    }
}
