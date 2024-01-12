package CSCB525.FN099857.Tansport.Company.copany;

import CSCB525.FN099857.Tansport.Company.client.Client;
import CSCB525.FN099857.Tansport.Company.employee.Employee;
import CSCB525.FN099857.Tansport.Company.mtv.MotorisedTransportVehicle;
import CSCB525.FN099857.Tansport.Company.route.Route;
import CSCB525.FN099857.Tansport.Company.transport.Transport;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a transport company entity with attributes such as company name, creation date, employees, motorized transport vehicles (MTVs),
 * routes, clients, and budget.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "company")
public class Company {

    /**
     * The unique identifier for the company.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * The name of the company. Required and should not be blank.
     */
    @NotBlank(message = "Company name is required")
    @Column(name = "name")
    private String companyName;

    /**
     * The date of creation for the company. Required, should not be null, and must be in the past or present.
     */
    @NotNull(message = "Creation date must not be null")
    @PastOrPresent(message = "Creation date must be in the past or present")
    @Column(name = "date_of_creation")
    private LocalDateTime created;

    /**
     * List of employees associated with the company.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Employee> employees = new ArrayList<>();

    /**
     * List of motorized transport vehicles (MTVs) owned by the company.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MotorisedTransportVehicle> mtvList = new ArrayList<>();

    /**
     * List of routes managed by the company.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Route> routes = new ArrayList<>();

    /**
     * List of clients associated with the company through a many-to-many relationship.
     */
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "company_client",
            joinColumns = @JoinColumn(name = "company_id"),
            inverseJoinColumns = @JoinColumn(name = "client_id")
    )
    private List<Client> clientsList = new ArrayList<>();

    /**
     * The budget of the company. Required and should not be null.
     */
    @NotNull(message = "Budget must not be null")
    private BigDecimal budget = BigDecimal.ZERO;

    @JsonIgnore
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transport> transports = new ArrayList<>();


    /**
     * Overrides the default toString method to provide a customized string representation of the company.
     *
     * @return A string representation of the company object.
     */
    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", companyName='" + companyName + '\'' +
                ", created=" + created +
                ", employees=" + employees.size() +
                ", mtvList=" + mtvList.size() +
                ", routes=" + routes.size() +
                ", clientsList=" + clientsList.size() +
                ", budget=" + budget +
                ", transports=" + transports.size() +
                '}';
    }

}
