package CSCB525.FN099857.Tansport.Company.transport;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a Commodity entity, which is a type of Transport with additional attributes specific to commodities.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Commodity extends Transport {

    /**
     * The weight of the commodity.
     */
    @Min(value = 0, message = "Weight must be a non-negative value")
    @Column(name = "commodity_weight")
    private double weight;

    /**
     * Returns a string representation of the Commodity entity.
     *
     * @return A string containing the weight of the commodity.
     */
    @Override
    public String toString() {
        return "Commodity{" +
                "weight=" + weight +
                '}';
    }
}
