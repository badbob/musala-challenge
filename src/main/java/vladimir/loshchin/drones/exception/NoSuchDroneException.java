package vladimir.loshchin.drones.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NoSuchDroneException extends ResponseStatusException {

    public NoSuchDroneException(String id) {
        super(HttpStatus.NOT_FOUND, String.format("No such drone with id=%s", id));
    }
}
