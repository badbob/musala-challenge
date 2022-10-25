package vladimir.loshchin.drones.model;

import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Drone {

    @Id @Length(max = 100)
    private String serial;

    @NotNull
    private DroneModel model;

    @NotNull
    private DroneStatus status;

    /**
     * Battery charge percentage
     */
    @Max(1)
    @Min(0)
    private double batteryCharge;

    @ElementCollection
    @CollectionTable(name = "drone_payload", joinColumns = @JoinColumn(name = "drone_id"))
    private Set<PayloadItem> payload;

}
