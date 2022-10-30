package vladimir.loshchin.drones.rest;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vladimir.loshchin.drones.dao.DroneRepo;
import vladimir.loshchin.drones.dao.MedicationRepo;
import vladimir.loshchin.drones.exception.DroneOutOfBatteryException;
import vladimir.loshchin.drones.exception.DroneOverloadedException;
import vladimir.loshchin.drones.exception.InvalidDroneStatusException;
import vladimir.loshchin.drones.exception.NoSuchDroneException;
import vladimir.loshchin.drones.exception.NoSuchMedicationException;
import vladimir.loshchin.drones.model.Drone;
import vladimir.loshchin.drones.model.DroneStatus;
import vladimir.loshchin.drones.model.PayloadItem;

import static java.util.stream.Collectors.toList;

import java.math.BigDecimal;

@RestController
@RequestMapping("/drone")
public class DroneController {

    private static final Set<DroneStatus> statusesAllowedForLoading
        = Set.of(DroneStatus.IDLE, DroneStatus.LOADING);

    private static final double BATTERY_THRESHOLD = 0.25;

    @Autowired
    private DroneRepo droneRepo;

    @Autowired
    private MedicationRepo medicationRepo;

    @PostMapping
    public void create(@Valid @RequestBody Drone drone) {
        droneRepo.save(drone);
    }

    @GetMapping(path = "/list")
    public List<Drone> list() {
        return droneRepo.findAll();
    }

    @GetMapping(path = "/list/available")
    public List<Drone> listAvailable() {
        return droneRepo.findAllByStatusInAndBatteryChargeGreaterThanEqual(
            Set.of(DroneStatus.IDLE, DroneStatus.LOADING), new BigDecimal("0.25"));
    }

    @GetMapping(path = "/{serial}")
    public Drone get(@PathVariable String serial) {
        return droneRepo.findById(serial)
            .orElseThrow(() -> new NoSuchDroneException(serial));
    }

    @PutMapping(path = "/{id}/load/{medicationCode}")
    public void load(@PathVariable String id, @PathVariable String medicationCode)
            throws NoSuchDroneException,
                   NoSuchMedicationException,
                   DroneOverloadedException {

        var drone = droneRepo.findById(id).orElseThrow(() -> new NoSuchDroneException(id));
        var medication = medicationRepo.findById(medicationCode)
            .orElseThrow(() -> new NoSuchMedicationException(medicationCode));

        if (drone.payloadWeight() + medication.getWeight() > drone.getModel().maxLoad()) {
            throw new DroneOverloadedException(drone);
        }

        if (!statusesAllowedForLoading.contains(drone.getStatus())) {
            throw new InvalidDroneStatusException(drone, statusesAllowedForLoading);
        }

        if (drone.getBatteryCharge().doubleValue() < BATTERY_THRESHOLD) {
            throw new DroneOutOfBatteryException(drone, BATTERY_THRESHOLD);
        }

        var pi = drone.getPayload().stream()
            .filter(i -> i.getMedication().equals(medication)).findFirst();

        pi.ifPresentOrElse(PayloadItem::increment, () -> {
                var newpi = new PayloadItem();
                newpi.setMedication(medication);
                newpi.setQuantity(1);

                drone.getPayload().add(newpi);
            });


        if (drone.getStatus().equals(DroneStatus.IDLE)) {
            drone.setStatus(DroneStatus.LOADING);
        }

        droneRepo.save(drone);
    }

    @GetMapping(path = "/{serial}/history")
    public List<Map<String, ?>> droneHistory(@PathVariable String serial) {
        return droneRepo.findRevisions(serial).stream()
            .map(r -> {
                    return Map.of(
                        "timestamp", r.getRequiredRevisionInstant(),
                        "revision", r.getRequiredRevisionNumber(),
                        "drone", r.getEntity());
                })
            .collect(toList());
    }
}
