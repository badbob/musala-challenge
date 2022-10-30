package vladimir.loshchin.drones.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import vladimir.loshchin.drones.model.Drone;
import vladimir.loshchin.drones.model.DroneStatus;

public interface DroneRepo extends JpaRepository<Drone, String> {

    List<Drone> findAllByStatusAndBatteryChargeLessThan(
        DroneStatus status, double charge);
}
