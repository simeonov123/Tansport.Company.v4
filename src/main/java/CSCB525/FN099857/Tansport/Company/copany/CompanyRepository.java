package CSCB525.FN099857.Tansport.Company.copany;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for the {@link Company} entity. Provides CRUD operations and custom queries for retrieving company information.
 */
@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

    /**
     * Retrieves an optional {@link Company} instance based on the provided company name.
     *
     * @param name The name of the company.
     * @return An optional {@link Company} instance.
     */
    Optional<Company> findByCompanyName(String name);

    /**
     * Retrieves a list of {@link Company} instances with a specific company name.
     *
     * @param companyName The name of the company.
     * @return A list of {@link Company} instances.
     */
    List<Company> findAllByCompanyName(String companyName);

    /**
     * Retrieves an optional {@link Company} instance based on the provided budget.
     *
     * @param budget The budget of the company.
     * @return An optional {@link Company} instance.
     */
    Optional<Company> findByBudget(BigDecimal budget);

    /**
     * Retrieves a list of {@link Company} instances with a budget greater than the provided amount.
     *
     * @param budget The minimum budget value for filtering.
     * @return A list of {@link Company} instances.
     */
    @Query("SELECT c FROM Company c WHERE c.budget > :budget")
    List<Company> findAllByBudgetMoreThan(@Param("budget") BigDecimal budget);

    /**
     * Retrieves a list of {@link Company} instances with a company name containing the specified string (case-insensitive).
     *
     * @param companyName The partial or complete name of the company for filtering.
     * @return A list of {@link Company} instances.
     */
    @Query("SELECT c FROM Company c WHERE LOWER(c.companyName) LIKE LOWER(CONCAT('%', :companyName, '%'))")
    List<Company> findByCompanyNameContainingIgnoreCase(@Param("companyName") String companyName);

    /**
     * Retrieves a list of {@link Company} instances with a budget greater than the provided revenue.
     *
     * @param revenue The minimum revenue value for filtering.
     * @return A list of {@link Company} instances.
     */
    List<Company> findByBudgetGreaterThan(BigDecimal revenue);
}
