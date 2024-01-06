package CSCB525.FN099857.Tansport.Company.route;

import CSCB525.FN099857.Tansport.Company.client.Client;
import CSCB525.FN099857.Tansport.Company.copany.Company;
import CSCB525.FN099857.Tansport.Company.employee.Employee;
import CSCB525.FN099857.Tansport.Company.mtv.MotorisedTransportVehicle;
import CSCB525.FN099857.Tansport.Company.transport.Transport;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents a route entity with attributes such as departure and arrival details, timing, price, distance,
 * assigned driver, transport, associated vehicles, and client information.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "route")
public class Route {

    /**
     * The unique identifier for the route.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * The company associated with the route. Required and should not be null.
     */
    @NotNull(message = "Company must not be null")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    /**
     * The departure address for the route. Required and should not be blank.
     */
    @NotBlank(message = "Departure address is required")
    @Column(name = "departure_address")
    private String departureAddress;

    /**
     * The arrival address for the route. Required and should not be blank.
     */
    @NotBlank(message = "Arrival address is required")
    @Column(name = "arrival_address")
    private String arrivalAddress;

    /**
     * The departure time for the route. Required, should not be null, and must be in the future or present.
     */
    @NotNull(message = "Departure time must not be null")
    @FutureOrPresent(message = "Departure time must be in the future or present")
    @Column(name = "departure_time")
    private LocalDateTime departureTime;

    /**
     * The arrival time for the route. Required, should not be null, and must be in the future.
     */
    @NotNull(message = "Arrival time must not be null")
    @Future(message = "Arrival time must be in the future")
    @Column(name = "arrival_time")
    private LocalDateTime arrivalTime;

    /**
     * The price of the route. Required, should not be null, and must be positive.
     */
    @NotNull(message = "Route price must not be null")
    @Positive(message = "Route price must be positive")
    @Column(name = "price")
    private BigDecimal routePrice;

    /**
     * The distance of the route. Required, should not be null, and must be positive.
     */
    @NotNull(message = "Distance must not be null")
    @Positive(message = "Distance must be positive")
    @Column(name = "distance")
    private Double distance;

    /**
     * The driver assigned to the route. Required and should not be null.
     */
    @NotNull(message = "Driver must not be null")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id")
    private Employee driver;

    /**
     * The transport associated with the route. Required and should not be null.
     */
    @NotNull(message = "Transport must not be null")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transport_id")
    private Transport transport;

    /**
     * The list of motorized transport vehicles associated with the route. Should have at least one vehicle.
     */
    @Size(min = 1, message = "At least one vehicle must be associated with the route")
    @ManyToMany
    @JoinTable(
            name = "vehicle_route",
            joinColumns = @JoinColumn(name = "route_id"),
            inverseJoinColumns = @JoinColumn(name = "vehicle_id")
    )
    private List<MotorisedTransportVehicle> vehicles;

    /**
     * The client associated with the route through a one-to-one relationship.
     */
    @OneToOne
    private Client client;

    /**
     * Overrides the default toString method to provide a customized string representation of the route.
     *
     * @return A string representation of the route object.
     */
    @Override
    public String toString() {
        return "Route{" +
                "id=" + id +
                ", company=" + company.getId() +
                ", departureAddress='" + departureAddress + '\'' +
                ", arrivalAddress='" + arrivalAddress + '\'' +
                ", departureTime=" + departureTime +
                ", arrivalTime=" + arrivalTime +
                ", routePrice=" + routePrice +
                ", distance=" + distance +
                ", driver=" + driver.getId() +
                ", transport=" + transport.getId() +
                ", vehicle=" + vehicles.size() +
                ", client=" + client.getId() +
                '}';
    }
}
