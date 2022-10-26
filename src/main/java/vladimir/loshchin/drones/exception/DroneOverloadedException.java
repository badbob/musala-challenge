package vladimir.loshchin.drones.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import vladimir.loshchin.drones.model.Drone;

public class DroneOverloadedException extends ResponseStatusException {

    public DroneOverloadedException(Drone drone) {
        super(HttpStatus.CONFLICT, String.format(
                  "Drone %s overloaded. It is already loaded with %d gr.",
                  drone.getSerial(), drone.payloadWeight()));
    }
}
