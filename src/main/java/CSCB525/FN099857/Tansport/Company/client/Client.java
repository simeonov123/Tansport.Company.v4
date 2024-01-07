package CSCB525.FN099857.Tansport.Company.client;

import CSCB525.FN099857.Tansport.Company.copany.Company;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * Represents a client entity in the transport company.
 * Clients are associated with multiple companies and have attributes such as name, phone number, and budget.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "client")
public class Client {

    /**
     * The unique identifier for the client.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * List of companies associated with the client.
     */
    @ManyToMany(mappedBy = "clientsList", fetch = FetchType.LAZY)
    private List<Company> companies;

    /**
     * The name of the client. Required and should not be blank.
     */
    @NotBlank(message = "Client name is required")
    private String name;

    /**
     * The phone number of the client. Required, should not be blank, and must be 10 digits.
     */
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
    private String phoneNumber;

    /**
     * The budget of the client. Required and should not be null.
     */
    @NotNull(message = "Budget is required")
    private BigDecimal budget;

    /**
     * Overrides the default toString method to provide a customized string representation of the client.
     *
     * @return A string representation of the client object.
     */
    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", companies=" + companies.size() +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", budget=" + budget +
                '}';
    }
}
