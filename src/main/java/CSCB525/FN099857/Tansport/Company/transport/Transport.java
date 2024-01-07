package CSCB525.FN099857.Tansport.Company.transport;

import CSCB525.FN099857.Tansport.Company.copany.Company;
import CSCB525.FN099857.Tansport.Company.route.Route;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a generic Transport entity, which serves as the base class for specific types of transportation within the system.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "transport")
public class Transport {

    /**
     * The unique identifier for the transport entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * The route associated with the transport.
     */
    @ManyToOne
    private Route route;

    /**
     * The company associated with the transport.
     */
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    /**
     * Returns a string representation of the Transport entity.
     *
     * @return A string containing the transport's ID, associated route ID, and associated company ID.
     */
    @Override
    public String toString() {
        return "Transport{" +
                "id=" + id +
                ", route=" + (route != null ? route.getId() : null) +
                ", company=" + (company != null ? company.getId() : null) +
                '}';
    }
}