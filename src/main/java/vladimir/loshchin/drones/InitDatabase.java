package vladimir.loshchin.drones;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import vladimir.loshchin.drones.dao.DroneRepo;
import vladimir.loshchin.drones.dao.MedicationRepo;
import vladimir.loshchin.drones.model.Drone;
import vladimir.loshchin.drones.model.DroneModel;
import vladimir.loshchin.drones.model.DroneStatus;
import vladimir.loshchin.drones.model.Medication;

@Configuration
public class InitDatabase {

    @Bean
    CommandLineRunner initDB(DroneRepo droneRepo, MedicationRepo medicationRepo) {
        return args -> {
            droneRepo.save(new Drone("SERIAL-1", DroneModel.LIGHT, DroneStatus.IDLE, 1.0, null));
            droneRepo.save(new Drone("LOADED-DRONE", DroneModel.MIDDLE, DroneStatus.LOADED, 1.0, null));

            medicationRepo.save(new Medication("ASPIRIN", "Aspirin", 20, null));
        };
    }
}
