package vladimir.loshchin.drones;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import vladimir.loshchin.drones.dao.DroneRepo;
import vladimir.loshchin.drones.model.Drone;
import vladimir.loshchin.drones.model.DroneModel;
import vladimir.loshchin.drones.model.DroneStatus;

@Configuration
public class InitDatabase {

    @Bean
    CommandLineRunner initDB(DroneRepo droneRepo) {
        return args -> {
            droneRepo.save(new Drone("SERIAL-1", DroneModel.LIGHT, DroneStatus.IDLE, 1.0));
        };
    }
}
