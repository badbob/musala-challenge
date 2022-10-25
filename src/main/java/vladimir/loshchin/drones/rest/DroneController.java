package vladimir.loshchin.drones.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vladimir.loshchin.drones.dao.DroneRepo;
import vladimir.loshchin.drones.model.Drone;

@RestController
@RequestMapping("/drone")
public class DroneController {

    @Autowired
    private DroneRepo droneRepo;

    @GetMapping(path = "/list")
    public List<Drone> list() {
        return droneRepo.findAll();
    }
}
