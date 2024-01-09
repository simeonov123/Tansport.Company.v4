package CSCB525.FN099857.Tansport.Company;

import CSCB525.FN099857.Tansport.Company.copany.Company;
import CSCB525.FN099857.Tansport.Company.copany.CompanyService;
import CSCB525.FN099857.Tansport.Company.employee.DriversLicense;
import CSCB525.FN099857.Tansport.Company.employee.Employee;
import CSCB525.FN099857.Tansport.Company.employee.EmployeeService;
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

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class EmployeeTest {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private EmployeeService employeeService;


    @Transactional
    @Test
    void testCreateEmployee() {
        List<Employee> employees = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Employee employee = new Employee();
            employee.setFirstName("employeeFirstName" + i);
            employee.setLastname("employeeLastName" + i);
            employee.setEmail("employeeEmail" + i);
            employee.setMonthlySalary(BigDecimal.valueOf(2000));
            employee.setPhoneNumber("088347594" + i);
            employee.setAddress("employeeAddress" + i);
            employee.setEgn("123456789" + i);
            switch (i) {
                case 0:
                    employee.setDriversLicense(DriversLicense.C1);
                    break;
                case 1:
                    employee.setDriversLicense(DriversLicense.D1E);
                    break;
                case 2:
                    employee.setDriversLicense(DriversLicense.C1E);
                    break;
                case 3:
                    employee.setDriversLicense(DriversLicense.TTM);
                    break;
                case 4:
                    employee.setDriversLicense(DriversLicense.B);
                    break;
                default:
                    System.out.println("Invalid option");
            }

            employees.add(employee);
        }

        List<Employee> savedEmployees = new ArrayList<>();
        for (Employee employee : employees) {
            Employee createdEmployee = employeeService.createEmployee(employee);
            savedEmployees.add(createdEmployee);
        }

        assertEquals(employees.size(), savedEmployees.size());
    }

    @Transactional
    @Test
    void testDeleteEmployeeById() {
        List<Employee> employees = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Employee employee = new Employee();
            employee.setFirstName("employeeFirstName" + i);
            employee.setLastname("employeeLastName" + i);
            employee.setEmail("employeeEmail" + i);
            employee.setMonthlySalary(BigDecimal.valueOf(2000));
            employee.setPhoneNumber("088347594" + i);
            employee.setAddress("employeeAddress" + i);
            employee.setEgn("123456789" + i);
            switch (i) {
                case 0:
                    employee.setDriversLicense(DriversLicense.C1);
                    break;
                case 1:
                    employee.setDriversLicense(DriversLicense.D1E);
                    break;
                case 2:
                    employee.setDriversLicense(DriversLicense.C1E);
                    break;
                case 3:
                    employee.setDriversLicense(DriversLicense.TTM);
                    break;
                case 4:
                    employee.setDriversLicense(DriversLicense.B);
                    break;
                default:
                    System.out.println("Invalid option");
            }

            employees.add(employee);
        }

        List<Employee> savedEmployees = new ArrayList<>();
        for (Employee employee : employees) {
            Employee createdEmployee = employeeService.createEmployee(employee);
            savedEmployees.add(createdEmployee);
        }

        //delete one employee
        employeeService.deleteEmployeeById(savedEmployees.get(0).getId());

        //try to retrieve that employee
        Employee deletedEmployee = employeeService.getEmployeeById(savedEmployees.get(0).getId());

        assertNull(deletedEmployee);
    }


    @Transactional
    @Test
    void testDeleteEmployeeByEgn() {
        List<Employee> employees = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Employee employee = new Employee();
            employee.setFirstName("employeeFirstName" + i);
            employee.setLastname("employeeLastName" + i);
            employee.setEmail("employeeEmail" + i);
            employee.setMonthlySalary(BigDecimal.valueOf(2000));
            employee.setPhoneNumber("088347594" + i);
            employee.setAddress("employeeAddress" + i);
            employee.setEgn("123456789" + i);
            switch (i) {
                case 0:
                    employee.setDriversLicense(DriversLicense.C1);
                    break;
                case 1:
                    employee.setDriversLicense(DriversLicense.D1E);
                    break;
                case 2:
                    employee.setDriversLicense(DriversLicense.C1E);
                    break;
                case 3:
                    employee.setDriversLicense(DriversLicense.TTM);
                    break;
                case 4:
                    employee.setDriversLicense(DriversLicense.B);
                    break;
                default:
                    System.out.println("Invalid option");
            }

            employees.add(employee);
        }

        List<Employee> savedEmployees = new ArrayList<>();
        for (Employee employee : employees) {
            Employee createdEmployee = employeeService.createEmployee(employee);
            savedEmployees.add(createdEmployee);
        }

        //delete one employee
        employeeService.deleteEmployeeByEgn(savedEmployees.get(0).getEgn());

        //try to retrieve that employee
        Employee deletedEmployee = employeeService.getEmployeeByEgn(savedEmployees.get(0).getEgn());

        assertNull(deletedEmployee);
    }


    @Transactional
    @Test
    void testGetAllEmployeesByAscendingSalaryViaCompanyId() {
        // 1A. Creating a company
        Company company = new Company();
        company.setCompanyName("MVI");
        company.setBudget(BigDecimal.valueOf(20000));
        company.setCreated(LocalDateTime.now());
        company = companyService.createCompany(company);

        List<Employee> employees = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 5; i++) {
            Employee employee = new Employee();
            employee.setCompany(company);
            employee.setDateOfHiring(LocalDateTime.now());
            employee.setFirstName("employeeFirstName" + i);
            employee.setLastname("employeeLastName" + i);
            employee.setEmail("employeeEmail" + i);

            // Generate a random salary between 750 and 2500
            BigDecimal randomSalary = BigDecimal.valueOf(750 + random.nextDouble() * (2500 - 750));
            employee.setMonthlySalary(randomSalary);

            employee.setPhoneNumber("088347594" + i);
            employee.setAddress("employeeAddress" + i);
            employee.setEgn("123456789" + i);
            switch (i) {
                case 0:
                    employee.setDriversLicense(DriversLicense.C1);
                    break;
                case 1:
                    employee.setDriversLicense(DriversLicense.D1E);
                    break;
                case 2:
                    employee.setDriversLicense(DriversLicense.C1E);
                    break;
                case 3:
                    employee.setDriversLicense(DriversLicense.TTM);
                    break;
                case 4:
                    employee.setDriversLicense(DriversLicense.B);
                    break;
                default:
                    System.out.println("Invalid option");
            }

            employees.add(employee);
        }

        List<Employee> savedEmployees = new ArrayList<>();
        for (Employee employee : employees) {
            Employee createdEmployee = employeeService.createEmployee(employee);
            savedEmployees.add(createdEmployee);
        }

        company = companyService.getCompanyByName(company.getCompanyName());

        savedEmployees = employeeService.getAllEmployeesByAscendingSalaryOrderByCompanyId(company.getId());

        // Assert that the list is sorted by ascending salary
        assertThat(savedEmployees)
                .isSortedAccordingTo(Comparator.comparing(Employee::getMonthlySalary));
    }


    @Transactional
    @Test
    void testGetAllEmployeesViaCompanyId() {
        // 1A. Creating a company
        Company company = new Company();
        company.setCompanyName("MVI");
        company.setBudget(BigDecimal.valueOf(20000));
        company.setCreated(LocalDateTime.now());
        company = companyService.createCompany(company);

        List<Employee> employees = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 5; i++) {
            Employee employee = new Employee();
            employee.setCompany(company);
            employee.setDateOfHiring(LocalDateTime.now());
            employee.setFirstName("employeeFirstName" + i);
            employee.setLastname("employeeLastName" + i);
            employee.setEmail("employeeEmail" + i);

            // Generate a random salary between 750 and 2500
            BigDecimal randomSalary = BigDecimal.valueOf(750 + random.nextDouble() * (2500 - 750));
            employee.setMonthlySalary(randomSalary);

            employee.setPhoneNumber("088347594" + i);
            employee.setAddress("employeeAddress" + i);
            employee.setEgn("123456789" + i);
            switch (i) {
                case 0:
                    employee.setDriversLicense(DriversLicense.C1);
                    break;
                case 1:
                    employee.setDriversLicense(DriversLicense.D1E);
                    break;
                case 2:
                    employee.setDriversLicense(DriversLicense.C1E);
                    break;
                case 3:
                    employee.setDriversLicense(DriversLicense.TTM);
                    break;
                case 4:
                    employee.setDriversLicense(DriversLicense.B);
                    break;
                default:
                    System.out.println("Invalid option");
            }

            employees.add(employee);
        }

        List<Employee> savedEmployees = new ArrayList<>();
        for (Employee employee : employees) {
            Employee createdEmployee = employeeService.createEmployee(employee);
            savedEmployees.add(createdEmployee);
        }

        company = companyService.getCompanyByName(company.getCompanyName());

        savedEmployees = employeeService.getAllEmployeesByCompanyId(company.getId());

        // Assert that the list is correctly retrieved based on the company ID
        Company finalCompany = company;
        assertThat(savedEmployees)
                .extracting(Employee::getCompany)
                .allMatch(retrievedCompany -> retrievedCompany.getId().equals(finalCompany.getId()));
    }


    @Transactional
    @Test
    void testGetAllEmployeesByCompanyIdAndCategory() {
        // 1A. Creating a company
        Company company = new Company();
        company.setCompanyName("MVI");
        company.setBudget(BigDecimal.valueOf(20000));
        company.setCreated(LocalDateTime.now());
        company = companyService.createCompany(company);

        List<Employee> employees = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 5; i++) {
            Employee employee = new Employee();
            employee.setCompany(company);
            employee.setDateOfHiring(LocalDateTime.now());
            employee.setFirstName("employeeFirstName" + i);
            employee.setLastname("employeeLastName" + i);
            employee.setEmail("employeeEmail" + i);

            // Generate a random salary between 750 and 2500
            BigDecimal randomSalary = BigDecimal.valueOf(750 + random.nextDouble() * (2500 - 750));
            employee.setMonthlySalary(randomSalary);

            employee.setPhoneNumber("088347594" + i);
            employee.setAddress("employeeAddress" + i);
            employee.setEgn("123456789" + i);
            switch (i) {
                case 0, 1:
                    employee.setDriversLicense(DriversLicense.C1);
                    break;
                case 2, 4, 3:
                    employee.setDriversLicense(DriversLicense.B);
                    break;
                default:
                    System.out.println("Invalid option");
            }

            employees.add(employee);
        }

        for (Employee employee : employees) {
            employeeService.createEmployee(employee);
        }

        company = companyService.getCompanyByName(company.getCompanyName());

        List<Employee> employeeCategoryC1 = employeeService.getAllEmployeesByCompanyIdAndCategory(company.getId(), DriversLicense.C1);

        List<Employee> employeeCategoryB = employeeService.getAllEmployeesByCompanyIdAndCategory(company.getId(), DriversLicense.B);

        assertEquals(2, employeeCategoryC1.size(), "C1 list mismatch.");
        assertEquals(3, employeeCategoryB.size(), "B list mismatch.");
    }


    @Transactional
    @Test
    void testGetAllEmployeesByDescendingSalaryOrderByCompany() {
        // 1A. Creating a company
        Company company = new Company();
        company.setCompanyName("MVI");
        company.setBudget(BigDecimal.valueOf(20000));
        company.setCreated(LocalDateTime.now());
        company = companyService.createCompany(company);

        List<Employee> employees = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 5; i++) {
            Employee employee = new Employee();
            employee.setCompany(company);
            employee.setDateOfHiring(LocalDateTime.now());
            employee.setFirstName("employeeFirstName" + i);
            employee.setLastname("employeeLastName" + i);
            employee.setEmail("employeeEmail" + i);

            // Generate a random salary between 750 and 2500
            BigDecimal randomSalary = BigDecimal.valueOf(750 + random.nextDouble() * (2500 - 750));
            employee.setMonthlySalary(randomSalary);

            employee.setPhoneNumber("088347594" + i);
            employee.setAddress("employeeAddress" + i);
            employee.setEgn("123456789" + i);
            switch (i) {
                case 0, 1:
                    employee.setDriversLicense(DriversLicense.C1);
                    break;
                case 2, 4, 3:
                    employee.setDriversLicense(DriversLicense.B);
                    break;
                default:
                    System.out.println("Invalid option");
            }

            employees.add(employee);
        }

        for (Employee employee : employees) {
            employeeService.createEmployee(employee);
        }

        company = companyService.getCompanyByName(company.getCompanyName());

        List<Employee> employeeListDescendingSalary = employeeService.getAllEmployeesByDescendingSalaryOrderByCompanyId(company.getId());

        assertThat(employeeListDescendingSalary)
                .isSortedAccordingTo(Comparator.comparing(Employee::getMonthlySalary, Comparator.reverseOrder()));
    }


    @Transactional
    @Test
    void testGetAllEmployeesSortedBySalary() {
        // 1A. Creating a company
        Company company = new Company();
        company.setCompanyName("MVI");
        company.setBudget(BigDecimal.valueOf(20000));
        company.setCreated(LocalDateTime.now());
        company = companyService.createCompany(company);

        List<Employee> employees = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 5; i++) {
            Employee employee = new Employee();
            employee.setCompany(company);
            employee.setDateOfHiring(LocalDateTime.now());
            employee.setFirstName("employeeFirstName" + i);
            employee.setLastname("employeeLastName" + i);
            employee.setEmail("employeeEmail" + i);

            // Generate a random salary between 750 and 2500
            BigDecimal randomSalary = BigDecimal.valueOf(750 + random.nextDouble() * (2500 - 750));
            employee.setMonthlySalary(randomSalary);

            employee.setPhoneNumber("088347594" + i);
            employee.setAddress("employeeAddress" + i);
            employee.setEgn("123456789" + i);
            switch (i) {
                case 0, 1:
                    employee.setDriversLicense(DriversLicense.C1);
                    break;
                case 2, 4, 3:
                    employee.setDriversLicense(DriversLicense.B);
                    break;
                default:
                    System.out.println("Invalid option");
            }

            employees.add(employee);
        }

        for (Employee employee : employees) {
            employeeService.createEmployee(employee);
        }

        // 1A. Creating a company
        Company company2 = new Company();
        company2.setCompanyName("MV2");
        company2.setBudget(BigDecimal.valueOf(220000));
        company2.setCreated(LocalDateTime.now());
        company2 = companyService.createCompany(company2);

        company2 = companyService.getCompanyByName(company2.getCompanyName());


        employees = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Employee employee = new Employee();
            employee.setCompany(company2);
            employee.setDateOfHiring(LocalDateTime.now());
            employee.setFirstName("employeeFirstName" + i);
            employee.setLastname("employeeLastName" + i);
            employee.setEmail("employeeEmail" + i);

            // Generate a random salary between 750 and 2500
            BigDecimal randomSalary = BigDecimal.valueOf(750 + random.nextDouble() * (2500 - 750));
            employee.setMonthlySalary(randomSalary);

            employee.setPhoneNumber("088347594" + i);
            employee.setAddress("employeeAddress" + i);
            employee.setEgn("123456789" + i);
            switch (i) {
                case 0, 1:
                    employee.setDriversLicense(DriversLicense.C1);
                    break;
                case 2, 4, 3:
                    employee.setDriversLicense(DriversLicense.B);
                    break;
                default:
                    System.out.println("Invalid option");
            }

            employees.add(employee);
        }

        for (Employee employee : employees) {
            employeeService.createEmployee(employee);
        }


        List<Employee> allEmployeesEver = employeeService.getAllEmployeesSortedBySalary();

        assertThat(allEmployeesEver)
                .isSortedAccordingTo(Comparator.comparing(Employee::getMonthlySalary, Comparator.reverseOrder()));
    }


    @Transactional
    @Test
    void testGetEmployeeByEgn() {
        List<Employee> employees = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 5; i++) {
            Employee employee = new Employee();
            employee.setFirstName("employeeFirstName" + i);
            employee.setLastname("employeeLastName" + i);
            employee.setEmail("employeeEmail" + i);

            // Generate a random salary between 750 and 2500
            BigDecimal randomSalary = BigDecimal.valueOf(750 + random.nextDouble() * (2500 - 750));
            employee.setMonthlySalary(randomSalary);

            employee.setPhoneNumber("088347594" + i);
            employee.setAddress("employeeAddress" + i);
            employee.setEgn("123456789" + i);
            switch (i) {
                case 0, 1:
                    employee.setDriversLicense(DriversLicense.C1);
                    break;
                case 2, 4, 3:
                    employee.setDriversLicense(DriversLicense.B);
                    break;
                default:
                    System.out.println("Invalid option");
            }

            employees.add(employee);
        }

        for (Employee originalEmployee : employees) {
            employeeService.createEmployee(originalEmployee);

            // Retrieve the employee by EGN
            Employee retrievedEmployee = employeeService.getEmployeeByEgn(originalEmployee.getEgn());

            // Assert that the retrieved employee is not null
            assertThat(retrievedEmployee).isNotNull();

            // Assert that the retrieved employee matches the original one
            assertThat(retrievedEmployee.getEgn()).isEqualTo(originalEmployee.getEgn());
            assertThat(retrievedEmployee.getFirstName()).isEqualTo(originalEmployee.getFirstName());
            assertThat(retrievedEmployee.getLastname()).isEqualTo(originalEmployee.getLastname());
            assertThat(retrievedEmployee.getEmail()).isEqualTo(originalEmployee.getEmail());
            assertThat(retrievedEmployee.getMonthlySalary()).isEqualTo(originalEmployee.getMonthlySalary());
            assertThat(retrievedEmployee.getPhoneNumber()).isEqualTo(originalEmployee.getPhoneNumber());
            assertThat(retrievedEmployee.getAddress()).isEqualTo(originalEmployee.getAddress());
            assertThat(retrievedEmployee.getDriversLicense()).isEqualTo(originalEmployee.getDriversLicense());
        }
    }


    @Transactional
    @Test
    void testGetEmployeeById() {
        List<Employee> employees = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 5; i++) {
            Employee employee = new Employee();
            employee.setFirstName("employeeFirstName" + i);
            employee.setLastname("employeeLastName" + i);
            employee.setEmail("employeeEmail" + i);

            // Generate a random salary between 750 and 2500
            BigDecimal randomSalary = BigDecimal.valueOf(750 + random.nextDouble() * (2500 - 750));
            employee.setMonthlySalary(randomSalary);

            employee.setPhoneNumber("088347594" + i);
            employee.setAddress("employeeAddress" + i);
            employee.setEgn("123456789" + i);
            switch (i) {
                case 0, 1:
                    employee.setDriversLicense(DriversLicense.C1);
                    break;
                case 2, 4, 3:
                    employee.setDriversLicense(DriversLicense.B);
                    break;
                default:
                    System.out.println("Invalid option");
            }

            employees.add(employee);
        }

        for (Employee originalEmployee : employees) {
            Employee createdEmployee = employeeService.createEmployee(originalEmployee);

            // Retrieve the employee by ID
            Employee retrievedEmployee = employeeService.getEmployeeById(createdEmployee.getId());

            // Assert that the retrieved employee is not null
            assertThat(retrievedEmployee).isNotNull();

            // Assert that the retrieved employee matches the original one
            assertThat(retrievedEmployee.getId()).isEqualTo(createdEmployee.getId());
            assertThat(retrievedEmployee.getEgn()).isEqualTo(originalEmployee.getEgn());
            assertThat(retrievedEmployee.getFirstName()).isEqualTo(originalEmployee.getFirstName());
            assertThat(retrievedEmployee.getLastname()).isEqualTo(originalEmployee.getLastname());
            assertThat(retrievedEmployee.getEmail()).isEqualTo(originalEmployee.getEmail());
            assertThat(retrievedEmployee.getMonthlySalary()).isEqualTo(originalEmployee.getMonthlySalary());
            assertThat(retrievedEmployee.getPhoneNumber()).isEqualTo(originalEmployee.getPhoneNumber());
            assertThat(retrievedEmployee.getAddress()).isEqualTo(originalEmployee.getAddress());
            assertThat(retrievedEmployee.getDriversLicense()).isEqualTo(originalEmployee.getDriversLicense());
        }
    }


    @Transactional
    @Test
    void testGetAllEmployeeByCategory() {
        // 1A. Creating a company
        Company company = new Company();
        company.setCompanyName("MVI");
        company.setBudget(BigDecimal.valueOf(20000));
        company.setCreated(LocalDateTime.now());
        company = companyService.createCompany(company);

        List<Employee> employees = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 5; i++) {
            Employee employee = new Employee();
            employee.setCompany(company);
            employee.setDateOfHiring(LocalDateTime.now());
            employee.setFirstName("employeeFirstName" + i);
            employee.setLastname("employeeLastName" + i);
            employee.setEmail("employeeEmail" + i);

            // Generate a random salary between 750 and 2500
            BigDecimal randomSalary = BigDecimal.valueOf(750 + random.nextDouble() * (2500 - 750));
            employee.setMonthlySalary(randomSalary);

            employee.setPhoneNumber("088347594" + i);
            employee.setAddress("employeeAddress" + i);
            employee.setEgn("123456789" + i);
            switch (i) {
                case 0, 1:
                    employee.setDriversLicense(DriversLicense.C1);
                    break;
                case 2, 4, 3:
                    employee.setDriversLicense(DriversLicense.B);
                    break;
                default:
                    System.out.println("Invalid option");
            }

            employees.add(employee);
        }

        for (Employee employee : employees) {
            employeeService.createEmployee(employee);
        }

        companyService.getCompanyByName(company.getCompanyName());

        List<Employee> employeeCategoryC1 = employeeService.getEmployeesByQualification(DriversLicense.C1);

        for (Employee employee: employeeCategoryC1) {

            assertEquals(DriversLicense.C1, employee.getDriversLicense(), "C1 list mismatch.");
        }
    }


    @Transactional
    @Test
    void testUpdateEmployee() {

        Random random = new Random();

        Employee employee = new Employee();
        employee.setFirstName("employeeFirstName");
        employee.setLastname("employeeLastName");
        employee.setEmail("employeeEmail");

        // Generate a random salary between 750 and 2500
        BigDecimal randomSalary = BigDecimal.valueOf(750 + random.nextDouble() * (2500 - 750));
        employee.setMonthlySalary(randomSalary);

        employee.setPhoneNumber("088347594");
        employee.setAddress("employeeAddress");
        employee.setEgn("123456789");

        employee.setDriversLicense(DriversLicense.C1);


        employeeService.createEmployee(employee);

        employee = employeeService.getEmployeeByEgn("123456789");

        // 1A. Creating a company
        Company company = new Company();
        company.setCompanyName("MVI");
        company.setBudget(BigDecimal.valueOf(20000));
        company.setCreated(LocalDateTime.now());
        company = companyService.createCompany(company);

        assertNull(employee.getCompany());

        employee.setCompany(company);
        employee.setDateOfHiring(LocalDateTime.now());

        employee = employeeService.updateEmployee(employee);

        assertEquals(company.getId(), employee.getCompany().getId());


    }
}


