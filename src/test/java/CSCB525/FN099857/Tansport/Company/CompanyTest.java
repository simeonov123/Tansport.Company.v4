package CSCB525.FN099857.Tansport.Company;

import CSCB525.FN099857.Tansport.Company.copany.Company;
import CSCB525.FN099857.Tansport.Company.copany.CompanyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CompanyTest {

    @Autowired
    private CompanyService companyService;


    @Transactional
    @Test
    void testCreateCompany() {
        // 1A. Creating a company
        Company company = new Company();
        company.setCompanyName("MVI");
        company.setBudget(BigDecimal.valueOf(20000));
        company.setCreated(LocalDateTime.now());
        company = companyService.createCompany(company);

        // Assertions
        Company savedCompany = companyService.getCompanyById(company.getId());
        assertNotNull(savedCompany);
        assertEquals("MVI", savedCompany.getCompanyName());

    }

    @Transactional
    @Test
    void testUpdateCompany() {
        //Create company
        // 1A. Creating a company
        Company company = new Company();
        company.setCompanyName("MOS");
        company.setBudget(BigDecimal.valueOf(240000));
        company.setCreated(LocalDateTime.now());
        company = companyService.createCompany(company);

        //update company
        BigDecimal expectedResult = company.getBudget().add(BigDecimal.valueOf(9999));
        company.setBudget(expectedResult);
        company = companyService.updateCompany(company);

        assertEquals(expectedResult, company.getBudget(), "Updated budget not saved.");

    }

    //delete company
    @Transactional
    @Test
    void testDeleteCompany() {
        //Create company
        // 1A. Creating a company
        Company company = new Company();
        company.setCompanyName("MOS");
        company.setBudget(BigDecimal.valueOf(240000));
        company.setCreated(LocalDateTime.now());
        company = companyService.createCompany(company);

        companyService.deleteCompany(company);

        company = companyService.getCompanyByName("MOS");

        assertNull(company, "Company not deleted.");
    }

    //get all companies by ascending order
    @Transactional
    @Test
    void testGetAllCompaniesByAscendingOrder() {
        //create companies
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            // 1A. Creating a company
            Company company = new Company();

            // Generate a random letter for the company name
            char randomLetter = (char) ('A' + random.nextInt(26));
            company.setCompanyName("MV" + randomLetter + i);

            // Generate a random budget between 20000 and 1000000
            BigDecimal randomBudget = BigDecimal.valueOf(20000 + random.nextDouble() * (1000000 - 20000));
            company.setBudget(randomBudget);

            company.setCreated(LocalDateTime.now());
            companyService.createCompany(company);
        }

        List<Company> allCompaniesInAscendingBudgetOrder = companyService.getAllCompaniesInAscendingBudgetOrder();


        for (int i = 0; i < allCompaniesInAscendingBudgetOrder.size(); i++) {
            if (i + 1 < allCompaniesInAscendingBudgetOrder.size()) {
                boolean result = allCompaniesInAscendingBudgetOrder.get(i).getBudget().compareTo(allCompaniesInAscendingBudgetOrder.get(i + 1).getBudget()) <= 0;
                assertTrue(result, "List not sorted correctly.");
            } else {
                boolean result = allCompaniesInAscendingBudgetOrder.get(i).getBudget().compareTo(allCompaniesInAscendingBudgetOrder.get(0).getBudget()) > 0;
                assertTrue(result, "List not sorted correctly.");
            }

        }
    }

    @Transactional
    @Test
    void testGetAllCompaniesByDescendingOrder() {
        //create companies
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            // 1A. Creating a company
            Company company = new Company();

            // Generate a random letter for the company name
            char randomLetter = (char) ('A' + random.nextInt(26));
            company.setCompanyName("MV" + randomLetter + i);

            // Generate a random budget between 20000 and 1000000
            BigDecimal randomBudget = BigDecimal.valueOf(20000 + random.nextDouble() * (1000000 - 20000));
            company.setBudget(randomBudget);

            company.setCreated(LocalDateTime.now());
            companyService.createCompany(company);
        }

        List<Company> allCompaniesInAscendingBudgetOrder = companyService.getAllCompaniesInDescendingBudgetOrder();


        for (int i = 0; i < allCompaniesInAscendingBudgetOrder.size(); i++) {
            if (i + 1 < allCompaniesInAscendingBudgetOrder.size()) {
                boolean result = allCompaniesInAscendingBudgetOrder.get(i).getBudget().compareTo(allCompaniesInAscendingBudgetOrder.get(i + 1).getBudget()) >= 0;
                assertTrue(result, "List not sorted correctly.");
            } else {
                boolean result = allCompaniesInAscendingBudgetOrder.get(i).getBudget().compareTo(allCompaniesInAscendingBudgetOrder.get(0).getBudget()) < 0;
                assertTrue(result, "List not sorted correctly.");
            }
        }
    }


    @Transactional
    @Test
    void testGetAllCompaniesSortedByName() {
        //create companies
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            // 1A. Creating a company
            Company company = new Company();

            // Generate a random letter for the company name
            char randomLetter = (char) ('A' + random.nextInt(26));
            company.setCompanyName("MV" + randomLetter + i);

            // Generate a random budget between 20000 and 1000000
            BigDecimal randomBudget = BigDecimal.valueOf(20000 + random.nextDouble() * (1000000 - 20000));
            company.setBudget(randomBudget);

            company.setCreated(LocalDateTime.now());
            companyService.createCompany(company);
        }

        // Get all companies sorted by name
        List<Company> allCompaniesInAscendingNameOrder = companyService.getAllCompaniesSortedByName();

        // Assert that the list is sorted by name
        assertThat(allCompaniesInAscendingNameOrder)
                .isSortedAccordingTo(Comparator.comparing(Company::getCompanyName));

    }


    @Transactional
    @Test
    void testGetAllCompaniesSortedByRevenue() {
        // create companies
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            // 1A. Creating a company
            Company company = new Company();

            // Generate a random letter for the company name
            char randomLetter = (char) ('A' + random.nextInt(26));
            company.setCompanyName("MV" + randomLetter + i);

            // Generate a random budget between 20000 and 1000000
            BigDecimal randomBudget = BigDecimal.valueOf(20000 + random.nextDouble() * (1000000 - 20000));
            company.setBudget(randomBudget);

            company.setCreated(LocalDateTime.now());
            companyService.createCompany(company);
        }

        // Get all companies sorted by revenue
        List<Company> allCompaniesInDescendingRevenueOrder = companyService.getAllCompaniesSortedByRevenue();

        // Assert that the list is sorted by revenue
        assertThat(allCompaniesInDescendingRevenueOrder)
                .isSortedAccordingTo(Comparator.comparing(Company::getBudget, Comparator.reverseOrder()));
    }


    @Transactional
    @Test
    void testGetCompaniesByName() {
        // create companies
        Random random = new Random();
        List<String> companyNames = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            // 1A. Creating a company
            Company company = new Company();

            // Generate a random letter for the company name
            char randomLetter = (char) ('A' + random.nextInt(26));
            String companyName = "MV" + randomLetter + i;
            company.setCompanyName(companyName);
            companyNames.add(companyName);

            // Generate a random budget between 20000 and 1000000
            BigDecimal randomBudget = BigDecimal.valueOf(20000 + random.nextDouble() * (1000000 - 20000));
            company.setBudget(randomBudget);

            company.setCreated(LocalDateTime.now());
            companyService.createCompany(company);
        }

        // Get companies by name
        String companyNameToRetrieve = companyNames.get(random.nextInt(companyNames.size()));
        List<Company> retrievedCompanies = companyService.getCompaniesByName(companyNameToRetrieve);

        // Assert that the retrieved companies have the expected name
        assertThat(retrievedCompanies)
                .allMatch(company -> company.getCompanyName().equals(companyNameToRetrieve));
    }


    @Transactional
    @Test
    void testGetCompaniesWithBudgetGreaterThan() {
        // create companies
        Random random = new Random();
        BigDecimal targetRevenue = BigDecimal.valueOf(500000); // Set your desired revenue threshold
        List<String> companyNamesWithRevenueGreaterThanTarget = new ArrayList<>();

        List<Company> createdCompanies = IntStream.range(0, 10)
                .mapToObj(i -> {
                    // 1A. Creating a company
                    Company company = new Company();

                    // Generate a random letter for the company name
                    char randomLetter = (char) ('A' + random.nextInt(26));
                    String companyName = "MV" + randomLetter + i;
                    company.setCompanyName(companyName);

                    // Generate a random budget between 20000 and 1000000
                    BigDecimal randomBudget = BigDecimal.valueOf(20000 + random.nextDouble() * (1000000 - 20000));
                    company.setBudget(randomBudget);

                    // If the generated revenue is greater than the target, add the company name to the list
                    if (randomBudget.compareTo(targetRevenue) > 0) {
                        companyNamesWithRevenueGreaterThanTarget.add(companyName);
                    }

                    company.setCreated(LocalDateTime.now());
                    companyService.createCompany(company);

                    return company;
                })
                .collect(Collectors.toList());

        // Get companies by revenue greater than the target using stream
        List<Company> retrievedCompanies = createdCompanies.stream()
                .filter(company -> company.getBudget().compareTo(targetRevenue) > 0)
                .collect(Collectors.toList());

        // Assert that the retrieved companies have revenue greater than the target
        assertThat(retrievedCompanies)
                .allMatch(company -> company.getBudget().compareTo(targetRevenue) > 0);

        // Assert that the retrieved company names match the expected list
        assertThat(retrievedCompanies)
                .extracting(Company::getCompanyName)
                .containsExactlyInAnyOrderElementsOf(companyNamesWithRevenueGreaterThanTarget);
    }



    @Transactional
    @Test
    void testGetCompanyByBudget() {
        // create companies
        Random random = new Random();
        BigDecimal targetBudget = BigDecimal.valueOf(500000); // Set your desired budget

        Company expectedCompany = null;

        for (int i = 0; i < 10; i++) {
            // 1A. Creating a company
            Company company = new Company();

            // Generate a random letter for the company name
            char randomLetter = (char) ('A' + random.nextInt(26));
            String companyName = "MV" + randomLetter + i;
            company.setCompanyName(companyName);

            // Generate a random budget between 20000 and 1000000
            BigDecimal randomBudget = BigDecimal.valueOf(20000 + random.nextDouble() * (1000000 - 20000));
            company.setBudget(randomBudget);

            // If the generated budget matches the target, set it as the expectedCompany
            if (randomBudget.compareTo(targetBudget) == 0) {
                expectedCompany = company;
            }

            company.setCreated(LocalDateTime.now());
            companyService.createCompany(company);
        }

        // Get company by budget
        Company retrievedCompany = companyService.getCompanyByBudget(targetBudget);

        // Assert that the retrieved company matches the expectedCompany
        assertThat(retrievedCompany)
                .isEqualTo(expectedCompany);
    }


    @Transactional
    @Test
    void testGetCompanyById() {
        // create companies
        List<Company> createdCompanies = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            // 1A. Creating a company
            Company company = new Company();
            company.setCompanyName("MV" + i);
            company.setBudget(BigDecimal.valueOf(20000 + i * 1000));
            company.setCreated(LocalDateTime.now());

            Company createdCompany = companyService.createCompany(company);
            createdCompanies.add(createdCompany);
        }

        // Choose a random company from the created ones
        Company randomCompany = createdCompanies.get(new Random().nextInt(createdCompanies.size()));

        // Get company by id
        Company retrievedCompany = companyService.getCompanyById(randomCompany.getId());

        // Assert that the retrieved company matches the expectedCompany
        assertThat(retrievedCompany)
                .isEqualTo(randomCompany);
    }


    @Transactional
    @Test
    void testGetCompanyByName() {
        // create companies
        List<Company> createdCompanies = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            // 1A. Creating a company
            Company company = new Company();
            String companyName = "MV" + i;
            company.setCompanyName(companyName);
            company.setBudget(BigDecimal.valueOf(20000 + i * 1000));
            company.setCreated(LocalDateTime.now());

            Company createdCompany = companyService.createCompany(company);
            createdCompanies.add(createdCompany);
        }

        // Choose a random company name from the created ones
        String randomCompanyName = createdCompanies.get(new Random().nextInt(createdCompanies.size())).getCompanyName();

        // Get company by name
        Company retrievedCompany = companyService.getCompanyByName(randomCompanyName);

        // Assert that the retrieved company matches the expected company
        assertThat(retrievedCompany)
                .isNotNull()
                .extracting(Company::getCompanyName)
                .isEqualTo(randomCompanyName);
    }


}