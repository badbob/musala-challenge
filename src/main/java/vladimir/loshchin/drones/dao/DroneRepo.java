package vladimir.loshchin.drones.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import vladimir.loshchin.drones.model.Drone;

public interface DroneRepo extends JpaRepository<Drone, String> {

}
