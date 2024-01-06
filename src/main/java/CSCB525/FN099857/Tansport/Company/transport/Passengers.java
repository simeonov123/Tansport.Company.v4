package CSCB525.FN099857.Tansport.Company.transport;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Represents a Passengers entity, which is a type of Transport with additional attributes specific to passenger transportation.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Passengers extends Transport {

    /**
     * The number of passengers that can be accommodated.
     */
    @Min(value = 0, message = "Number of passengers must be a non-negative value")
    @Column(name = "number_of_passengers")
    private int numberOfPassengers;

    /**
     * Returns a string representation of the Passengers entity.
     *
     * @return A string containing the number of passengers.
     */
    @Override
    public String toString() {
        return "Passengers{" +
                "numberOfPassengers=" + numberOfPassengers +
                '}';
    }
}