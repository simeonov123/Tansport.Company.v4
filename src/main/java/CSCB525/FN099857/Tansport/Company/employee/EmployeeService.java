package CSCB525.FN099857.Tansport.Company.employee;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing {@link Employee} entities, providing methods for creating, updating, and deleting employees,
 * as well as retrieving employee information based on various criteria.
 */
@Service
@Transactional
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    /**
     * Constructs an {@code EmployeeService} instance with the provided {@link EmployeeRepository}.
     *
     * @param employeeRepository The repository for accessing employee data.
     */
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /**
     * Creates a new employee by saving it to the repository.
     *
     * @param employee The employee to be created. Should be a valid, non-null, and properly annotated instance.
     * @return created employee
     */
    public Employee createEmployee(@Valid Employee employee) {
        employeeRepository.save(employee);
        return employee;
    }

    /**
     * Updates an existing employee by saving the modified information to the repository.
     *
     * @param employee The employee to be updated. Should be a valid, non-null, and properly annotated instance.
     * @return updated employee
     */
    public Employee updateEmployee(@Valid Employee employee) {
        return employeeRepository.save(employee);
    }

    /**
     * Deletes an employee based on their Unique Civil Registration Number (EGN).
     *
     * @param egn The Unique Civil Registration Number (EGN) of the employee to be deleted.
     */
    @Transactional
    public void deleteEmployeeByEgn(@NotBlank String egn) {
        employeeRepository.deleteByEgn(egn);
    }

    /**
     * Deletes an employee based on their unique identifier.
     *
     * @param id The unique identifier of the employee to be deleted.
     */
    @Transactional
    public void deleteEmployeeById(@NotNull Integer id) {
        employeeRepository.deleteById(id);
    }

    /**
     * Retrieves an employee based on their Unique Civil Registration Number (EGN).
     *
     * @param egn The Unique Civil Registration Number (EGN) of the employee.
     * @return An optional containing the employee with the specified EGN, or empty if not found.
     */
    public Employee getEmployeeByEgn(@NotBlank String egn) {
        Optional<Employee> employee = employeeRepository.findByEgn(egn);
        return employee.orElse(null);
    }

    /**
     * Retrieves a list of employees associated with a specific company identified by its unique identifier.
     *
     * @param companyId The unique identifier of the company.
     * @return A list of employees associated with the specified company.
     */
    public List<Employee> getAllEmployeesByCompanyId(@NotNull Integer companyId) {
        return employeeRepository.findAllByCompanyId(companyId);
    }

    /**
     * Retrieves a list of employees with a specific driver's license category associated with a particular company.
     *
     * @param id             The unique identifier of the company.
     * @param driversLicense The driver's license category.
     * @return A list of employees with the specified driver's license category associated with the specified company.
     */
    public List<Employee> getAllEmployeesByCompanyIdAndCategory(@NotNull Integer id, @NotNull DriversLicense driversLicense) {
        return employeeRepository.findAllByDriversLicenseAndCompanyId(id, driversLicense);
    }

    /**
     * Retrieves a list of employees associated with a specific company, sorted in ascending order based on their monthly salary.
     *
     * @param id The unique identifier of the company.
     * @return A list of employees associated with the specified company, sorted by ascending monthly salary.
     */
    public List<Employee> getAllEmployeesByAscendingSalaryOrderByCompanyId(@NotNull Integer id) {
        List<Employee> employees = employeeRepository.findAllByCompanyId(id);
        employees.sort(Comparator.comparing(Employee::getMonthlySalary));
        return employees;
    }

    /**
     * Retrieves a list of employees associated with a specific company, sorted in descending order based on their monthly salary.
     *
     * @param id The unique identifier of the company.
     * @return A list of employees associated with the specified company, sorted by descending monthly salary.
     */
    public List<Employee> getAllEmployeesByDescendingSalaryOrderByCompanyId(@NotNull Integer id) {
        List<Employee> employees = employeeRepository.findAllByCompanyId(id);
        employees.sort(Comparator.comparing(Employee::getMonthlySalary).reversed());
        return employees;
    }

    /**
     * Retrieves a list of all employees, sorted in descending order based on their monthly salary.
     *
     * @return A list of all employees, sorted by descending monthly salary.
     */
    public List<Employee> getAllEmployeesSortedBySalary() {
        return employeeRepository.findAll(Sort.by(Sort.Direction.DESC, "monthlySalary"));
    }

    /**
     * Retrieves a list of employees with a specific driver's license category.
     *
     * @param driversLicense The driver's license category.
     * @return A list of employees with the specified driver's license category.
     */
    public List<Employee> getEmployeesByQualification(@NotNull DriversLicense driversLicense) {
        return employeeRepository.findByDriversLicense(driversLicense);
    }

    /**
     * Retrieves an employee based on their unique identifier.
     *
     * @param id The unique identifier of the employee.
     * @return An employee with the specified unique identifier, or null if not found.
     */
    public Employee getEmployeeById(@NotNull Integer id) {
        return employeeRepository.findById(id).orElse(null);
    }
}
