package CSCB525.FN099857.Tansport.Company.employee;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link Employee} entities, providing methods for CRUD operations and custom queries.
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    /**
     * Retrieves a list of employees associated with a specific company identified by its unique identifier.
     *
     * @param companyId The unique identifier of the company.
     * @return A list of employees associated with the specified company.
     */
    List<Employee> findAllByCompanyId(Integer companyId);

    /**
     * Deletes an employee based on their Unique Civil Registration Number (EGN).
     *
     * @param egn The Unique Civil Registration Number (EGN) of the employee to be deleted.
     */
    @Transactional
    @Modifying
    @Query("DELETE FROM Employee e WHERE e.egn = :egn")
    void deleteByEgn(@Param("egn") String egn);

    /**
     * Retrieves an employee based on their Unique Civil Registration Number (EGN).
     *
     * @param egn The Unique Civil Registration Number (EGN) of the employee.
     * @return An optional containing the employee with the specified EGN, or empty if not found.
     */
    @Query("SELECT e FROM Employee e WHERE e.egn = :egn")
    Optional<Employee> findByEgn(@Param("egn") String egn);

    /**
     * Retrieves a list of employees with a specific driver's license category associated with a particular company.
     *
     * @param companyId      The unique identifier of the company.
     * @param driversLicense The driver's license category.
     * @return A list of employees with the specified driver's license category associated with the specified company.
     */
    @Query("SELECT e FROM Employee e WHERE e.company.id = :companyId AND e.driversLicense = :driversLicense")
    List<Employee> findAllByDriversLicenseAndCompanyId(@Param("companyId") Integer companyId, @Param("driversLicense") DriversLicense driversLicense);

    /**
     * Retrieves a list of employees with a specific driver's license category.
     *
     * @param driversLicense The driver's license category.
     * @return A list of employees with the specified driver's license category.
     */
    List<Employee> findByDriversLicense(DriversLicense driversLicense);
}

