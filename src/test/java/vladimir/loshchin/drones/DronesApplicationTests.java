package vladimir.loshchin.drones;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.RequestEntity;

import vladimir.loshchin.drones.dao.DroneRepo;
import vladimir.loshchin.drones.model.Drone;
import vladimir.loshchin.drones.model.DroneModel;
import vladimir.loshchin.drones.model.DroneStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
class DronesApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Autowired
    private DroneRepo droneRepo;

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

    @Test
    void createDrone() {
        var drone = new Drone();
        drone.setSerial("NEW-DRONE");
        drone.setBatteryCharge(1.0);
        drone.setModel(DroneModel.HEAVY);
        drone.setStatus(DroneStatus.IDLE);

        restTemplate.postForObject("/drone", drone, Void.class);

        var persisted = droneRepo.findById(drone.getSerial()).get();

        assertEquals(drone.getSerial(), persisted.getSerial());
        assertEquals(drone.getBatteryCharge(), persisted.getBatteryCharge());
        assertEquals(drone.getModel(), persisted.getModel());
        assertEquals(drone.getStatus(), persisted.getStatus());
    }

    @Test
    void createDrone_serialNumOverflow() {
        var drone = new Drone();
        drone.setSerial("SUPER-LONG-0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000-SERIAL");
        drone.setBatteryCharge(1.0);
        drone.setModel(DroneModel.HEAVY);
        drone.setStatus(DroneStatus.IDLE);

        var resp = restTemplate.postForEntity("/drone", drone, Void.class);

        assertEquals(UNPROCESSABLE_ENTITY, resp.getStatusCode());
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
