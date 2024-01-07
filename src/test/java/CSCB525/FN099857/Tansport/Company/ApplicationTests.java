package CSCB525.FN099857.Tansport.Company;

import CSCB525.FN099857.Tansport.Company.client.Client;
import CSCB525.FN099857.Tansport.Company.client.ClientService;
import CSCB525.FN099857.Tansport.Company.copany.Company;
import CSCB525.FN099857.Tansport.Company.copany.CompanyService;
import CSCB525.FN099857.Tansport.Company.employee.DriversLicense;
import CSCB525.FN099857.Tansport.Company.employee.Employee;
import CSCB525.FN099857.Tansport.Company.employee.EmployeeService;
import CSCB525.FN099857.Tansport.Company.mtv.MotorisedTransportVehicle;
import CSCB525.FN099857.Tansport.Company.mtv.MotorisedTransportVehicleService;
import CSCB525.FN099857.Tansport.Company.mtv.VehicleType;
import CSCB525.FN099857.Tansport.Company.route.Route;
import CSCB525.FN099857.Tansport.Company.route.RouteService;
import CSCB525.FN099857.Tansport.Company.transport.Commodity;
import CSCB525.FN099857.Tansport.Company.transport.TransportService;
import CSCB525.FN099857.Tansport.Company.util.DriverProfitDTO;
import CSCB525.FN099857.Tansport.Company.util.DriverRouteDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CompanyServiceIntegrationTest {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private TransportService transportService;

    @Autowired
    private RouteService routeService;


    @Autowired
    private MotorisedTransportVehicleService mtvService;

    //1. Въвеждане, редактиране и изтриване на транспортна компания, която извършва
    //транспортни услуги и в която са наети служители

    //1. Въвеждане на компания
    @Transactional
    @Test
    void testCreateCompany() {
        // 1A. Creating a company
        Company company = new Company();
        company.setCompanyName("MVI");
        company.setBudget(BigDecimal.valueOf(20000));
        company.setCreated(LocalDateTime.now());
        companyService.createCompany(company);

        // Assertions
        Company savedCompany = companyService.getCompanyById(company.getId());
        assertNotNull(savedCompany);
        assertEquals("MVI", savedCompany.getCompanyName());

    }


    //1. Създавам компания създавам 5 служители и ги вкарвам в компанията
    @Transactional
    @Test
    void testCreateEmployeesAndCompany() {
        // 1A. Creating a company
        Company company = new Company();
        company.setCompanyName("MVI");
        company.setBudget(BigDecimal.valueOf(20000));
        company.setCreated(LocalDateTime.now());
        companyService.createCompany(company);

        // 1B. Creating employees and associating them with the company
        List<Employee> employees = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Employee employee = new Employee();
            employee.setCompany(company);
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

        //пълня лист със служители който да запазя в базата
        company.setEmployees(employees);

        //с update на company добавям служителите
        companyService.updateCompany(company);

        // Assertions
        Company savedCompany = companyService.getCompanyById(company.getId());
        assertNotNull(savedCompany);
        List<Employee> savedEmployees = employeeService.getAllEmployeesByCompanyId(savedCompany.getId());
        assertNotNull(savedEmployees);
        assertEquals(5, savedEmployees.size());
    }


    //създаване на клиенти
    @Transactional
    @Test
    void createClients() {
        //creating clients
        //2 задача. Въвеждане, редактиране и изтриване на клиентите на транспортната компания
        //създавам клиенти и ги добавям в лист
        List<Client> clients = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Client client = new Client();
            client.setName("clientName" + i);
            client.setPhoneNumber("088347594" + i);

            clients.add(client);

        }

        //2.А. запазвам клиентите в базата
        for (Client client : clients) {
            clientService.createClient(client);
        }

        //then I get the clients to assert
        List<Client> createdClients = clientService.getAllClients();

        assertEquals(clients.size(), createdClients.size(), "Created clients don't match the actual result.");
    }

    //редактиране на клиенти
    @Transactional
    @Test
    void assignClientToCompany() {
        // 1A. Creating a company
        Company company = new Company();
        company.setCompanyName("MVI");
        company.setBudget(BigDecimal.valueOf(20000));
        company.setCreated(LocalDateTime.now());
        companyService.createCompany(company);

        //creating clients
        //създавам клиенти и ги добавям в лист
        List<Client> clients = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Client client = new Client();
            client.setName("clientName" + i);
            client.setPhoneNumber("088347594" + i);

            clients.add(client);

        }

        //2.А. запазвам клиентите в базата
        for (Client client : clients) {
            clientService.createClient(client);
        }

        //then I get the clients to assert
        List<Client> createdClients = clientService.getAllClients();

        //set clients company
        for (Client client : createdClients) {
            List<Company> companies = new ArrayList<>();
            companies.add(company);
            client.setCompanies(companies);

            //update the clients
            clientService.updateClient(client);
        }


        //get all clients
        List<Client> updatedClients = clientService.getAllClients();

        for (Client client : updatedClients) {
            List<Company> updatedCompanyList = client.getCompanies();

            assertEquals(1, updatedCompanyList.size(), "Company list length doesn't match the expected result");
        }
    }




    @Transactional
    @Test
    void testGettingCompanyViaCompanyName() {

        // 1A. Creating companies with name
        for (int i = 0; i < 5; i++) {
            Company company = new Company();
            company.setCompanyName("MVI" + i);
            company.setBudget(BigDecimal.valueOf(20000 + i * 10));
            company.setCreated(LocalDateTime.now());
            companyService.createCompany(company);
        }

        Company company = companyService.getCompanyByName("MVI3");

        assertEquals(company.getCompanyName(), "MVI3");
    }

    @Transactional
    @Test
    void testGettingCompanyViaBudget() {
        // 1A. Creating companies
        for (int i = 0; i < 5; i++) {
            Company company = new Company();
            company.setCompanyName("MVI" + i);
            company.setBudget(BigDecimal.valueOf(20000 + i * 10));
            company.setCreated(LocalDateTime.now());
            companyService.createCompany(company);
        }

        Company company = companyService.getCompanyByBudget(BigDecimal.valueOf(20010));

        assertEquals(company.getCompanyName(), "MVI1");
    }


    @Transactional
    @Test
    void testSortingCompaniesListByDescendingOrderOfBudget() {
        // 1A. Creating companies
        for (int i = 5; i > 0; i--) {
            Company company = new Company();
            company.setCompanyName("MVI" + i);
            company.setBudget(BigDecimal.valueOf(20000 + i * 10L));
            company.setCreated(LocalDateTime.now());
            companyService.createCompany(company);
        }

        //I get the list of companies that interest me -> companies with budget over 20020
        List<Company> companies = companyService.getCompaniesWithBudgetMoreThan(BigDecimal.valueOf(20020));

        assertEquals(companies.size(), 3, "Companies List contains more companies tan the expected result.");

        //I sort the list by -> first lowest budget then highest budget
        companies.sort(Comparator.comparing(Company::getBudget));

        assertAll("Companies List Sorting",
                () -> assertEquals(-1, companies.get(0).getBudget().compareTo(companies.get(1).getBudget()), "Incorrectly sorted"),
                () -> assertEquals(-1, companies.get(1).getBudget().compareTo(companies.get(2).getBudget()), "Incorrectly sorted"),
                () -> assertEquals(1, companies.get(2).getBudget().compareTo(companies.get(0).getBudget()), "Incorrectly sorted")
        );
    }


    @Transactional
    @Test
    void testAscendingSortingOnCompaniesList() {
        // 1A. Creating companies
        for (int i = 5; i > 0; i--) {
            Company company = new Company();
            company.setCompanyName("MVI" + i);
            company.setBudget(BigDecimal.valueOf(20000 + i * 10L));
            company.setCreated(LocalDateTime.now());
            companyService.createCompany(company);
        }

        List<Company> companies = companyService.getAllCompaniesInAscendingBudgetOrder();

        assertAll("Companies List Sorting Ascending",
                () -> assertEquals(-1, companies.get(0).getBudget().compareTo(companies.get(1).getBudget()), "Incorrectly sorted"),
                () -> assertEquals(-1, companies.get(1).getBudget().compareTo(companies.get(2).getBudget()), "Incorrectly sorted"),
                () -> assertEquals(-1, companies.get(2).getBudget().compareTo(companies.get(3).getBudget()), "Incorrectly sorted"),
                () -> assertEquals(-1, companies.get(3).getBudget().compareTo(companies.get(4).getBudget()), "Incorrectly sorted"),
                () -> assertEquals(1, companies.get(4).getBudget().compareTo(companies.get(0).getBudget()), "Incorrectly sorted")
        );
    }


    @Transactional
    @Test
    void testDescendingSortingOnCompaniesList() {
        // 1A. Creating companies
        for (int i = 0; i < 5; i++) {
            Company company = new Company();
            company.setCompanyName("MVI" + i);
            company.setBudget(BigDecimal.valueOf(20000 + i * 10L));
            company.setCreated(LocalDateTime.now());
            companyService.createCompany(company);
        }

        List<Company> companies = companyService.getAllCompaniesInDescendingBudgetOrder();

        assertAll("Companies List Sorting Ascending",
                () -> assertEquals(1, companies.get(0).getBudget().compareTo(companies.get(1).getBudget()), "Incorrectly sorted"),
                () -> assertEquals(1, companies.get(1).getBudget().compareTo(companies.get(2).getBudget()), "Incorrectly sorted"),
                () -> assertEquals(1, companies.get(2).getBudget().compareTo(companies.get(3).getBudget()), "Incorrectly sorted"),
                () -> assertEquals(1, companies.get(3).getBudget().compareTo(companies.get(4).getBudget()), "Incorrectly sorted"),
                () -> assertEquals(-1, companies.get(4).getBudget().compareTo(companies.get(0).getBudget()), "Incorrectly sorted")
        );
    }


    //За служителите по квалификация
    @Transactional
    @Test
    void testGettingEmployeesByCategory() {

        // 1A. Creating a company
        Company company = new Company();
        company.setCompanyName("MVI");
        company.setBudget(BigDecimal.valueOf(20000));
        company.setCreated(LocalDateTime.now());
        companyService.createCompany(company);

        // 1B. Creating employees and associating them with the company
        List<Employee> employees = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Employee employee = new Employee();
            employee.setCompany(company);
            employee.setFirstName("employeeFirstName" + i);
            employee.setLastname("employeeLastName" + i);
            employee.setEmail("employeeEmail" + i);
            employee.setMonthlySalary(BigDecimal.valueOf(2000));
            employee.setPhoneNumber("088347594" + i);
            employee.setAddress("employeeAddress" + i);
            employee.setEgn("123456789" + i);
            switch (i) {
                case 0, 1:
                    employee.setDriversLicense(DriversLicense.C1);
                    break;
                case 2:
                    employee.setDriversLicense(DriversLicense.C1E);
                    break;
                case 3, 4:
                    employee.setDriversLicense(DriversLicense.B);
                    break;
                default:
                    System.out.println("Invalid option");
            }

            employees.add(employee);

        }

        //пълня лист със служители който да запазя в базата
        company.setEmployees(employees);

        //с update на company добавям служителите
        companyService.updateCompany(company);

        // Assertions
        Company savedCompany = companyService.getCompanyById(company.getId());
        assertNotNull(savedCompany);
        List<Employee> savedEmployees = employeeService.getAllEmployeesByCompanyIdAndCategory(savedCompany.getId(), DriversLicense.B);


        assertNotNull(savedEmployees);
        assertEquals(2, savedEmployees.size());
        // Add more assertions based on your requirements


    }

    ////За служителите по заплата
    @Transactional
    @Test
    void testGetAllEmployeesSalaryAscendingOrder() {
        // 1A. Creating a company
        Company company = new Company();
        company.setCompanyName("MVI");
        company.setBudget(BigDecimal.valueOf(20000));
        company.setCreated(LocalDateTime.now());
        companyService.createCompany(company);

        // 1B. Creating employees and associating them with the company
        List<Employee> employees = new ArrayList<>();
        for (int i = 5; i > 0; i--) {
            Employee employee = new Employee();
            employee.setCompany(company);
            employee.setFirstName("employeeFirstName" + i);
            employee.setLastname("employeeLastName" + i);
            employee.setEmail("employeeEmail" + i);
            employee.setMonthlySalary(BigDecimal.valueOf(2000 + i * 10L));
            employee.setPhoneNumber("088347594" + i);
            employee.setAddress("employeeAddress" + i);
            employee.setEgn("123456789" + i);
            switch (i) {
                case 0, 1:
                    employee.setDriversLicense(DriversLicense.C1);
                    break;
                case 2:
                    employee.setDriversLicense(DriversLicense.C1E);
                    break;
                case 3, 4:
                    employee.setDriversLicense(DriversLicense.B);
                    break;
                default:
                    System.out.println("Invalid option");
            }

            employees.add(employee);

        }

        //пълня лист със служители който да запазя в базата
        company.setEmployees(employees);

        //с update на company добавям служителите
        companyService.updateCompany(company);

        // Assertions
        Company savedCompany = companyService.getCompanyById(company.getId());
        assertNotNull(savedCompany);
        List<Employee> savedEmployees = employeeService.getAllEmployeesByAscendingSalaryOrderByCompanyId(savedCompany.getId());


        assertNotNull(savedEmployees);
        assertEquals(5, savedEmployees.size());
        // Add more assertions based on your requirements
        assertAll("Companies List Sorting Ascending",
                () -> assertEquals(1, employees.get(0).getMonthlySalary().compareTo(employees.get(1).getMonthlySalary()), "Incorrectly sorted"),
                () -> assertEquals(1, employees.get(1).getMonthlySalary().compareTo(employees.get(2).getMonthlySalary()), "Incorrectly sorted"),
                () -> assertEquals(1, employees.get(2).getMonthlySalary().compareTo(employees.get(3).getMonthlySalary()), "Incorrectly sorted"),
                () -> assertEquals(1, employees.get(3).getMonthlySalary().compareTo(employees.get(4).getMonthlySalary()), "Incorrectly sorted"),
                () -> assertEquals(-1, employees.get(4).getMonthlySalary().compareTo(employees.get(0).getMonthlySalary()), "Incorrectly sorted")
        );
    }


    @Transactional
    @Test
    void testGetAllEmployeesSalaryDescendingOrder() {
        // 1A. Creating a company
        Company company = new Company();
        company.setCompanyName("MVI");
        company.setBudget(BigDecimal.valueOf(20000));
        company.setCreated(LocalDateTime.now());
        companyService.createCompany(company);

        // 1B. Creating employees and associating them with the company
        List<Employee> employees = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Employee employee = new Employee();
            employee.setCompany(company);
            employee.setFirstName("employeeFirstName" + i);
            employee.setLastname("employeeLastName" + i);
            employee.setEmail("employeeEmail" + i);
            employee.setMonthlySalary(BigDecimal.valueOf(2000 + i * 10L));
            employee.setPhoneNumber("088347594" + i);
            employee.setAddress("employeeAddress" + i);
            employee.setEgn("123456789" + i);
            switch (i) {
                case 0, 1:
                    employee.setDriversLicense(DriversLicense.C1);
                    break;
                case 2:
                    employee.setDriversLicense(DriversLicense.C1E);
                    break;
                case 3, 4:
                    employee.setDriversLicense(DriversLicense.B);
                    break;
                default:
                    System.out.println("Invalid option");
            }

            employees.add(employee);

        }

        //пълня лист със служители който да запазя в базата
        company.setEmployees(employees);

        //с update на company добавям служителите
        companyService.updateCompany(company);

        // Assertions
        Company savedCompany = companyService.getCompanyById(company.getId());
        assertNotNull(savedCompany);
        List<Employee> savedEmployees = employeeService.getAllEmployeesByDescendingSalaryOrderByCompanyId(savedCompany.getId());


        assertNotNull(savedEmployees);
        assertEquals(5, savedEmployees.size());
        // Add more assertions based on your requirements
        assertAll("Companies List Sorting Ascending",
                () -> assertEquals(-1, employees.get(0).getMonthlySalary().compareTo(employees.get(1).getMonthlySalary()), "Incorrectly sorted"),
                () -> assertEquals(-1, employees.get(1).getMonthlySalary().compareTo(employees.get(2).getMonthlySalary()), "Incorrectly sorted"),
                () -> assertEquals(-1, employees.get(2).getMonthlySalary().compareTo(employees.get(3).getMonthlySalary()), "Incorrectly sorted"),
                () -> assertEquals(-1, employees.get(3).getMonthlySalary().compareTo(employees.get(4).getMonthlySalary()), "Incorrectly sorted"),
                () -> assertEquals(1, employees.get(4).getMonthlySalary().compareTo(employees.get(0).getMonthlySalary()), "Incorrectly sorted")
        );
    }


    //Показване на справки за общ брой извършени превози
    @Transactional
    @Test
    void testToGetSumOfRoutesForCompany() {

        // 1A. Creating a company
        Company company = new Company();
        company.setCompanyName("MVI");
        company.setBudget(BigDecimal.valueOf(20000));
        company.setCreated(LocalDateTime.now());
        company = companyService.createCompany(company);

        // creating one employee (I need a driver)
        Employee driver = new Employee();
        driver.setCompany(company);
        driver.setFirstName("employeeFirstName");
        driver.setLastname("employeeLastName");
        driver.setEmail("employeeEmail");
        driver.setMonthlySalary(BigDecimal.valueOf(2000));
        driver.setPhoneNumber("0883475940");
        driver.setAddress("employeeAddress");
        driver.setEgn("1234567890");
        driver.setDriversLicense(DriversLicense.C1);

        List<Employee> employees = new ArrayList<>();
        employees.add(driver);
        company.setEmployees(employees);

        //and also adding one vehicle
        MotorisedTransportVehicle vehicle = new MotorisedTransportVehicle();
        vehicle.setCompany(company);
        vehicle.setModel("vehicleModel");
        vehicle.setPurchasedOn(LocalDateTime.now());
        vehicle.setVehicleType(VehicleType.BUS);
        vehicle.setRequiredDriversLicenseCategory(DriversLicense.C1E);

        List<MotorisedTransportVehicle> mvtList = new ArrayList<>();
        mvtList.add(vehicle);
        company.setMtvList(mvtList);

        //we update the company with the employee and the vehicle
        companyService.updateCompany(company);

        company = companyService.getCompanyByName("MVI");

        assertEquals(company.getCompanyName(), "MVI", "Company name doesn't match.");
        assertEquals(company.getEmployees().size(), 1, "Company employee list size doesn't match.");
        assertEquals(company.getMtvList().size(), 1, "Company mvt list size doesn't match.");

        //we now need the transport (passengers or commodity)
        //lets say we are driving around commodity
        Commodity commodityTransport = new Commodity();
        commodityTransport.setWeight(2550.);
        commodityTransport.setCompany(company);

        //save the transport in the database
        commodityTransport = (Commodity) transportService.createTransport(commodityTransport);

        for (int i = 0; i < 10; i++) {
            Route route = new Route();
            route.setCompany(company);
            route.setDriver(employeeService.getEmployeeByEgn("1234567890"));
            route.setDepartureAddress("Departure Address");
            route.setArrivalAddress("Arrival Address");
            route.setDepartureTime(LocalDateTime.now());
            route.setArrivalTime(LocalDateTime.now().plusHours(5 + i));
            route.setDistance(330.0 + i * 2);
            route.setRoutePrice(BigDecimal.valueOf(550 + i * 2));
            route.setTransport(commodityTransport);
            List<MotorisedTransportVehicle> vehicles = new ArrayList<>();
            vehicles.add(vehicle);
            route.setVehicles(vehicles);

            //now save the transport in the database
            routeService.createRoute(route);
        }

        List<Route> routeList = routeService.getAllRoutesByCompanyId(company.getId());

        assertEquals(10, routeList.size());

    }












}
