package vladimir.loshchin.drones.model;

import java.sql.Blob;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Medication {

    @Id
    @Pattern(regexp = "^[A-Z]+[A-Z0-9_-]+$")
    private String code;

    @NotBlank @NotNull
    @Pattern(regexp = "^\\w+[\\w-]+$")
    private String name;

    @Min(1)
    private int weight /* gr */;

    private Blob image;
}
