package CSCB525.FN099857.Tansport.Company.copany;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing {@link Company} entities. This class provides business logic and interacts with the {@link CompanyRepository}.
 */
@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    /**
     * Constructs a new CompanyService with the specified CompanyRepository.
     *
     * @param companyRepository The repository for Company entities.
     */
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    /**
     * Creates a new company with the provided data.
     *
     * @param company The company to be created.
     * @return The created company.
     */
    @Transactional
    public Company createCompany(@Valid Company company) {
        return companyRepository.save(company);
    }

    /**
     * Updates an existing company with the provided data.
     *
     * @param company The company to be updated.
     * @return The updated company.
     */
    @Transactional
    public Company updateCompany(@Valid Company company) {
        return companyRepository.save(company);
    }

    /**
     * Deletes the specified company.
     *
     * @param company The company to be deleted.
     */
    @Transactional
    public void deleteCompany(Company company) {
        companyRepository.deleteById(company.getId());
    }

    /**
     * Deletes the company with the specified ID.
     *
     * @param id The ID of the company to be deleted.
     */
    @Transactional
    public void deleteCompanyById(Integer id) {
        companyRepository.deleteById(id);
    }

    /**
     * Retrieves a company by its ID.
     *
     * @param id The ID of the company.
     * @return An optional company, or null if not found.
     */
    public Company getCompanyById(Integer id) {
        Optional<Company> company = companyRepository.findById(id);
        return company.orElse(null);
    }

    /**
     * Retrieves a company by its name.
     *
     * @param name The name of the company.
     * @return An optional company, or null if not found.
     */
    public Company getCompanyByName(@NotBlank String name) {
        Optional<Company> company = companyRepository.findByCompanyName(name);
        return company.orElse(null);
    }

    /**
     * Retrieves a list of all companies sorted in ascending order based on their budget.
     *
     * @return A list of companies.
     */
    public List<Company> getAllCompaniesInAscendingBudgetOrder() {
        List<Company> companies = companyRepository.findAll();

        // Sorting the list based on budget using a custom comparator
        companies.sort(Comparator.comparing(Company::getBudget));

        return companies;
    }

    /**
     * Retrieves a list of all companies sorted in descending order based on their budget.
     *
     * @return A list of companies.
     */
    public List<Company> getAllCompaniesInDescendingBudgetOrder() {
        List<Company> companies = companyRepository.findAll();

        // Sorting the list based on budget using a custom comparator
        companies.sort(Comparator.comparing(Company::getBudget).reversed());

        return companies;
    }

    /**
     * Retrieves a company by its budget.
     *
     * @param budget The budget of the company.
     * @return An optional company, or null if not found.
     */
    public Company getCompanyByBudget(@NotNull BigDecimal budget) {
        return companyRepository.findByBudget(budget).orElse(null);
    }

    /**
     * Retrieves a list of companies with a budget greater than the provided amount.
     *
     * @param budget The minimum budget value for filtering.
     * @return A list of companies.
     */
    public List<Company> getCompaniesWithBudgetMoreThan(@NotNull BigDecimal budget) {
        return companyRepository.findAllByBudgetMoreThan(budget);
    }

    /**
     * Retrieves a list of all companies sorted in ascending order based on their names.
     *
     * @return A list of companies.
     */
    public List<Company> getAllCompaniesSortedByName() {
        return companyRepository.findAll(Sort.by(Sort.Direction.ASC, "companyName"));
    }

    /**
     * Retrieves a list of all companies sorted in descending order based on their revenue.
     *
     * @return A list of companies.
     */
    public List<Company> getAllCompaniesSortedByRevenue() {
        return companyRepository.findAll(Sort.by(Sort.Direction.DESC, "budget"));
    }

    /**
     * Retrieves a list of companies with names containing the specified string (case-insensitive).
     *
     * @param companyName The partial or complete name of the company for filtering.
     * @return A list of companies.
     */
    public List<Company> getCompaniesByName(String companyName) {
        return companyRepository.findByCompanyNameContainingIgnoreCase(companyName);
    }

    /**
     * Retrieves a list of companies with revenue greater than the provided amount.
     *
     * @param revenue The minimum revenue value for filtering.
     * @return A list of companies.
     */
    public List<Company> getCompaniesByRevenueGreaterThan(@NotNull BigDecimal revenue) {
        return companyRepository.findByBudgetGreaterThan(revenue);
    }
}
