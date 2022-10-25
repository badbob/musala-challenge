package vladimir.loshchin.drones.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vladimir.loshchin.drones.dao.DroneRepo;
import vladimir.loshchin.drones.dao.MedicationRepo;
import vladimir.loshchin.drones.exception.NoSuchDroneException;
import vladimir.loshchin.drones.exception.NoSuchMedicationException;
import vladimir.loshchin.drones.model.Drone;
import vladimir.loshchin.drones.model.PayloadItem;

@RestController
@RequestMapping("/drone")
public class DroneController {

    @Autowired
    private DroneRepo droneRepo;

    @Autowired
    private MedicationRepo medicationRepo;

    @GetMapping(path = "/list")
    public List<Drone> list() {
        return droneRepo.findAll();
    }

    @PutMapping(path = "/{id}/load/{medicationCode}")
    public void load(@PathVariable String id, @PathVariable String medicationCode) {
        var drone = droneRepo.findById(id).orElseThrow(() -> new NoSuchDroneException(id));
        var medication = medicationRepo.findById(medicationCode)
            .orElseThrow(() -> new NoSuchMedicationException(medicationCode));

        var pi = new PayloadItem();
        pi.setMedication(medication);
        pi.setQuantity(1);

        drone.getPayload().add(pi);

        droneRepo.save(drone);
    }
}
