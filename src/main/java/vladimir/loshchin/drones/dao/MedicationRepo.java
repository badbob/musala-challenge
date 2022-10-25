package vladimir.loshchin.drones.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import vladimir.loshchin.drones.model.Medication;

public interface MedicationRepo extends JpaRepository<Medication, String> {

}
