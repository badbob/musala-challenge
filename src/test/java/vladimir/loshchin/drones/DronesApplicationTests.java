package vladimir.loshchin.drones;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.RequestEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.CONFLICT;

@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
class DronesApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;


    @Test
    void loadUnexistentDrone() throws URISyntaxException {
        var resp = restTemplate.exchange(
            put("/drone/NOT-EXIST/load/ASPIRIN"), Void.class);

        assertEquals(NOT_FOUND, resp.getStatusCode());
    }

    @Test
    void loadDroneWithUnexistendMedication() throws URISyntaxException {
        var resp = restTemplate.exchange(
            put("/drone/SERIAL-1/load/NOT-EXIST"), Void.class);

        assertEquals(NOT_FOUND, resp.getStatusCode());
    }

    @Test
    void overloadDrone() {
        var resp = restTemplate.exchange(
            put("/drone/SERIAL-1/load/ASPIRIN"), Void.class);

        assertEquals(OK, resp.getStatusCode());

        resp = restTemplate.exchange(
            put("/drone/SERIAL-1/load/ASPIRIN"), Void.class);

        assertEquals(OK, resp.getStatusCode());

        resp = restTemplate.exchange(
            put("/drone/SERIAL-1/load/ASPIRIN"), Void.class);

        assertEquals(CONFLICT, resp.getStatusCode());
    }

    @Test
    void invalidStatusLoad() {
        var resp = restTemplate.exchange(
            put("/drone/LOADED-DRONE/load/ASPIRIN"), Void.class);

        assertEquals(CONFLICT, resp.getStatusCode());
    }

    @Test
    void outOfBattery() {
        var resp = restTemplate.exchange(
            put("/drone/OUT-OF-BATTERY/load/ASPIRIN"), Void.class);

        assertEquals(CONFLICT, resp.getStatusCode());
    }

    private RequestEntity<?> put(String path) {
        return new RequestEntity<>(PUT, uri(path));
    }

    private URI uri(String path) {
        try {
            return new URI(String.format("http://localhost:%d%s", port, path));
        } catch (URISyntaxException ex) {
            throw new IllegalStateException(ex);
        }
    }
}
