package vladimir.loshchin.drones.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NoSuchMedicationException extends ResponseStatusException {

    public NoSuchMedicationException(String medication) {
        super(HttpStatus.NOT_FOUND, String.format("No such medication: %s", medication));
    }
}
