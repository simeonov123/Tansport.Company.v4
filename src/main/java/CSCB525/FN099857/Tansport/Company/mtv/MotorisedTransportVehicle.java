package CSCB525.FN099857.Tansport.Company.mtv;

import CSCB525.FN099857.Tansport.Company.copany.Company;
import CSCB525.FN099857.Tansport.Company.employee.DriversLicense;
import CSCB525.FN099857.Tansport.Company.route.Route;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents a motorized transport vehicle (MTV) entity with attributes such as model, purchase date, associated routes,
 * owning company, vehicle type, and required driver's license category.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "motorised_transport_vehicle")
public class MotorisedTransportVehicle {

    /**
     * The unique identifier for the motorized transport vehicle.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * List of routes associated with the motorized transport vehicle through a many-to-many relationship.
     */
    @JsonIgnore
    @ManyToMany(mappedBy = "vehicles", fetch = FetchType.LAZY)
    private List<Route> routes;

    /**
     * The model of the motorized transport vehicle. Required and should not be blank.
     */
    @NotBlank(message = "Model is required")
    @Column(name = "model")
    private String model;

    /**
     * The date when the motorized transport vehicle was purchased. Should be in the past or present.
     */
    @PastOrPresent(message = "Purchased on date must be in the past or present")
    private LocalDateTime purchasedOn;

    /**
     * The owning company of the motorized transport vehicle.
     */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    /**
     * The type of the motorized transport vehicle. Required and should not be null.
     */
    @NotNull(message = "Vehicle type must not be null")
    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_type")
    private VehicleType vehicleType;

    /**
     * The required driver's license category for operating the motorized transport vehicle. Required and should not be null.
     */
    @NotNull(message = "Required driver's license category must not be null")
    @Enumerated(EnumType.STRING)
    @Column(name = "required_drivers_license_category")
    private DriversLicense requiredDriversLicenseCategory;

    /**
     * Overrides the default toString method to provide a customized string representation of the motorized transport vehicle.
     *
     * @return A string representation of the motorized transport vehicle object.
     */
    @Override
    public String toString() {
        return "MotorisedTransportVehicle{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", purchasedOn=" + purchasedOn +
                ", company=" + (company != null ? company.getId() : null) +
                ", vehicleType=" + vehicleType +
                ", requiredDriversLicenseCategory=" + requiredDriversLicenseCategory +
                '}';
    }
}