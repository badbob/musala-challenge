package vladimir.loshchin.drones.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

import vladimir.loshchin.drones.model.Drone;
import vladimir.loshchin.drones.model.DroneStatus;

public interface DroneRepo extends JpaRepository<Drone, String>,
                                   RevisionRepository<Drone, String, Long> {

    List<Drone> findAllByStatusAndBatteryChargeLessThan(
        DroneStatus status, BigDecimal charge);

    List<Drone> findAllByStatusInAndBatteryChargeGreaterThanEqual(
        Set<DroneStatus> statuses, BigDecimal threshold);
}
