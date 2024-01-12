package CSCB525.FN099857.Tansport.Company.employee;

import CSCB525.FN099857.Tansport.Company.copany.Company;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * Represents an employee within a transport company with attributes such as first name, last name, EGN (Unique Civil Registration Number),
 * monthly salary, phone number, address, email, driver's license category, and date of hiring.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employee")
public class Employee {

    /**
     * The unique identifier for the employee.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * The company to which the employee belongs.
     */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    /**
     * The first name of the employee. Required and should not be blank.
     */
    @NotBlank(message = "First name is required")
    @Column(name = "first_name")
    private String firstName;

    /**
     * The last name of the employee. Required and should not be blank.
     */
    @NotBlank(message = "Last name is required")
    @Column(name = "last_name")
    private String lastname;

    /**
     * The Unique Civil Registration Number (EGN) of the employee. Required and must be 10 characters.
     */
    @NotBlank(message = "EGN is required")
    @Size(min = 10, max = 10, message = "EGN must be 10 characters")
    @Column(name = "egn")
    private String egn;

    /**
     * The monthly salary of the employee. Required, should not be null, and must be greater than 0.
     */
    @NotNull(message = "Monthly salary must not be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Monthly salary must be greater than 0")
    @Column(name = "monthly_salary")
    private BigDecimal monthlySalary;

    /**
     * The phone number of the employee. Required and should not be blank.
     */
    @NotBlank(message = "Phone number is required")
    @Column(name = "phone_number")
    private String phoneNumber;

    /**
     * The address of the employee. Required and should not be blank.
     */
    @NotBlank(message = "Address is required")
    @Column(name = "address")
    private String address;

    /**
     * The email address of the employee. Required and should be in a valid email format.
     */
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Column(name = "email")
    private String email;

    /**
     * The category of the driver's license held by the employee. Required and should not be null.
     */
    @NotNull(message = "Driver's license category must not be null")
    @Enumerated(EnumType.STRING)
    @Column(name = "drivers_license_category")
    private DriversLicense driversLicense;

    /**
     * The date of hiring for the employee. Should be in the past or present.
     */
    @PastOrPresent(message = "Date of hiring must be in the past or present")
    @Column(name = "date_of_hiring")
    private LocalDateTime dateOfHiring;

    /**
     * Overrides the default toString method to provide a customized string representation of the employee.
     *
     * @return A string representation of the employee object.
     */
    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", company=" + (company != null ? company.getId() : null) +
                ", firstName='" + firstName + '\'' +
                ", lastname='" + lastname + '\'' +
                ", egn='" + egn + '\'' +
                ", monthlySalary=" + monthlySalary +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", dateOfHiring=" + dateOfHiring +
                ", driversLicense=" + driversLicense +
                '}';
    }
}