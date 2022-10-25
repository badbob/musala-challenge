package vladimir.loshchin.drones.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Medication {

    @Id
    private String code;

    @NotBlank @NotNull
    private String name;

    @Min(1)
    private int weight /* gr */;

    private byte[] image;
}
