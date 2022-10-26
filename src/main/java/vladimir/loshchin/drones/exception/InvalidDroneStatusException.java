package vladimir.loshchin.drones.exception;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import vladimir.loshchin.drones.model.Drone;
import vladimir.loshchin.drones.model.DroneStatus;

public class InvalidDroneStatusException extends ResponseStatusException {

    public InvalidDroneStatusException(Drone drone, Set<DroneStatus> expected) {
        super(HttpStatus.CONFLICT, String.format(
                  "Drone %s has invalid status %s. Expected: %s",
                  drone.getSerial(), drone.getStatus(), expected));
    }
}
