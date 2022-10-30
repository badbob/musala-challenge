package vladimir.loshchin.drones.rest;

import java.sql.SQLException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import vladimir.loshchin.drones.dao.MedicationRepo;
import vladimir.loshchin.drones.exception.NoSuchMedicationException;
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

    @RequestMapping("/{code}/image")
    public @ResponseBody ResponseEntity<InputStreamResource> image(@PathVariable String code) throws SQLException {

        var in = medicationRepo.findById(code)
            .map(Medication::getImage)
            .orElseThrow(() -> new NoSuchMedicationException(code))
            .getBinaryStream();

        return ResponseEntity.ok()
            .contentType(MediaType.IMAGE_JPEG)
            .body(new InputStreamResource(in));
    }
}
