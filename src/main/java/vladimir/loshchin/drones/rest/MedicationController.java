package vladimir.loshchin.drones.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vladimir.loshchin.drones.dao.MedicationRepo;
import vladimir.loshchin.drones.model.Medication;

@RestController @RequestMapping("/medication")
public class MedicationController {

    @Autowired
    private MedicationRepo medicationRepo;

    @RequestMapping("/list")
    public List<Medication> list() {
        return medicationRepo.findAll();
    }

    @PostMapping
    public void create(@Valid @RequestBody Medication medication) {
        medicationRepo.save(medication);
    }
}
