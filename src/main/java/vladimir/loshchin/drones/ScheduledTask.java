package vladimir.loshchin.drones;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import vladimir.loshchin.drones.dao.DroneRepo;
import vladimir.loshchin.drones.model.DroneStatus;

@Component
public class ScheduledTask {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTask.class);

    private static final double CHARGE_STEP = 0.1;

    @Autowired
    private DroneRepo droneRepo;

    /**
     * Chargint dron +10% each run
     */
    @Scheduled(fixedRate = 5000)
    public void chargingTask() {
        var idleDrones = droneRepo.findAllByStatusAndBatteryChargeLessThan(DroneStatus.IDLE, 1.0);

        idleDrones.forEach(d -> {
                if (d.getBatteryCharge() + CHARGE_STEP >= 1) {
                    d.setBatteryCharge(1.0);
                } else {
                    d.setBatteryCharge(d.getBatteryCharge() + CHARGE_STEP);
                }

                droneRepo.save(d);
            });

        log.info("Drones charged: {}", idleDrones.size());
    }
}
