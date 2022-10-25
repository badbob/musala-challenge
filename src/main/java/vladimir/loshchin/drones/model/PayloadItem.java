package vladimir.loshchin.drones.model;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;

import lombok.Data;
import lombok.NoArgsConstructor;


@Embeddable
@Data @NoArgsConstructor
public class PayloadItem {

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "medication_code")
    private Medication medication;

    @Min(1)
    private int quantity;
}
