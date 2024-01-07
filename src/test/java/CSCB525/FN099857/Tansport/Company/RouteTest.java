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
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest

public class RouteTest {
    @Autowired
    private CompanyService companyService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private MotorisedTransportVehicleService vehicleService;

    @Autowired
    private RouteService routeService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private TransportService transportService;


    //приходите на компанията за определен период от време
    @Transactional
    @Test
    void testingProfitForPeriodOfTime() {
        // 1A. Creating a company
        Company company = new Company();
        company.setCompanyName("MVI");
        company.setBudget(BigDecimal.valueOf(20000));
        company.setCreated(LocalDateTime.now());
        company = companyService.createCompany(company);

        // creating 5 employees
        List<Employee> employees = new ArrayList<>();


        Employee driver;
        for (int i = 0; i < 5; i++) {
            driver = new Employee();
            driver.setCompany(company);
            driver.setFirstName("employeeFirstName" + i);
            driver.setLastname("employeeLastName");
            driver.setEmail("employeeEmail");
            driver.setMonthlySalary(BigDecimal.valueOf(750));
            driver.setPhoneNumber("088347594");
            driver.setAddress("employeeAddress");
            driver.setEgn("123456789" + i);
            driver.setDriversLicense(DriversLicense.C1);

            employees.add(driver);
        }

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

        //we update the company with the employees and the vehicle
        companyService.updateCompany(company);

        company = companyService.getCompanyByName("MVI");

        assertEquals(company.getCompanyName(), "MVI", "Company name doesn't match.");
        assertEquals(company.getEmployees().size(), 5, "Company employee list size doesn't match.");
        assertEquals(company.getMtvList().size(), 1, "Company mvt list size doesn't match.");

        //we now need the transport (passengers or commodity)
        //lets say we are driving around commodity
        Commodity commodityTransport = new Commodity();
        commodityTransport.setWeight(2550.);
        commodityTransport.setCompany(company);

        //save the transport in the database
        commodityTransport = (Commodity) transportService.createTransport(commodityTransport);

        //we create 2 routes with driver with egn 1234567890
        for (int i = 0; i < 2; i++) {
            Route route = new Route();
            route.setCompany(company);
            route.setDriver(employeeService.getEmployeeByEgn("1234567890"));
            route.setDepartureAddress("Departure Address");
            route.setArrivalAddress("Arrival Address");
            route.setDepartureTime(LocalDateTime.of(2023, 7, 7, 8 + i, 0));
            route.setArrivalTime(LocalDateTime.of(2023, 7, 7, 13 + i, 0));
            route.setDistance(330.0 + i * 2);
            route.setRoutePrice(BigDecimal.valueOf(100 + i * 2));
            route.setTransport(commodityTransport);
            List<MotorisedTransportVehicle> vehicles = new ArrayList<>();
            vehicles.add(vehicle);
            route.setVehicles(vehicles);

            //then we create a client and add it to the route
            Client client = new Client();
            client.setName("clientName");
            client.setPhoneNumber("0883475940");
            client.setBudget(BigDecimal.valueOf(10000));

            //2.А. запазвам клиентите в базата
            client = clientService.createClient(client);

            boolean processedPayment = routeService.payRouteViaClient(client, route);

            assertTrue(processedPayment, "Payment Failed.");
            //now save the transport in the database
            routeService.createRoute(route);
        }

        //we create 6 routes with driver with egn 1234567891
        for (int i = 0; i < 6; i++) {
            Route route = new Route();
            route.setCompany(company);
            route.setDriver(employeeService.getEmployeeByEgn("1234567891"));
            route.setDepartureAddress("Departure Address");
            route.setArrivalAddress("Arrival Address");
            route.setDepartureTime(LocalDateTime.of(2023, 9, 7, 8 + i, 0));
            route.setArrivalTime(LocalDateTime.of(2023, 9, 7, 13 + i, 0));
            route.setDistance(330.0 + i * 2);
            route.setRoutePrice(BigDecimal.valueOf(1000 + i * 2));
            route.setTransport(commodityTransport);
            List<MotorisedTransportVehicle> vehicles = new ArrayList<>();
            vehicles.add(vehicle);
            route.setVehicles(vehicles);

            //then we create a client and add it to the route
            Client client = new Client();
            client.setName("clientName");
            client.setPhoneNumber("0883475940");
            client.setBudget(BigDecimal.valueOf(100000));

            //2.А. запазвам клиентите в базата
            client = clientService.createClient(client);

            boolean processedPayment = routeService.payRouteViaClient(client, route);

            assertTrue(processedPayment, "Payment Failed.");
            //now save the transport in the database
            routeService.createRoute(route);
        }

        //we create 4 routes with driver with egn 1234567893
        for (int i = 0; i < 4; i++) {
            Route route = new Route();
            route.setCompany(company);
            route.setDriver(employeeService.getEmployeeByEgn("1234567893"));
            route.setDepartureAddress("Departure Address");
            route.setArrivalAddress("Arrival Address");
            route.setDepartureTime(LocalDateTime.of(2023, 11, 7, 8 + i, 0));
            route.setArrivalTime(LocalDateTime.of(2023, 11, 7, 13 + i, 0));
            route.setDistance(330.0 + i * 2);
            route.setRoutePrice(BigDecimal.valueOf(1000 + i * 2));
            route.setTransport(commodityTransport);
            List<MotorisedTransportVehicle> vehicles = new ArrayList<>();
            vehicles.add(vehicle);
            route.setVehicles(vehicles);

            //then we create a client and add it to the route
            Client client = new Client();
            client.setName("clientName");
            client.setPhoneNumber("0883475940");
            client.setBudget(BigDecimal.valueOf(100000));

            //2.А. запазвам клиентите в базата
            client = clientService.createClient(client);

            boolean processedPayment = routeService.payRouteViaClient(client, route);

            assertTrue(processedPayment, "Payment Failed.");
            //now save the transport in the database
            routeService.createRoute(route);
        }

        // Calculate profit for the period from October 7, 2023, to December 7, 2023
        BigDecimal profitForPeriodOfTime = routeService.getProfitOfPeriodOfTimeByCompanyId(
                company.getId(),
                LocalDateTime.of(2023, 10, 7, 0, 0),
                LocalDateTime.of(2023, 12, 7, 23, 59)
        );

        // Asserting that profit is not null
        assertNotNull(profitForPeriodOfTime, "Profit should not be null");

        // Adjusted expected profit based on the specific scenario
        BigDecimal expectedProfit = new BigDecimal("262.00");

        // Asserting that the calculated profit matches the expected profit using compareTo
        assertEquals(0, profitForPeriodOfTime.compareTo(expectedProfit),
                "Profit for the period should match the expected profit");

    }


    @Test
    @Transactional
    void createRoute() {
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


        Route route = new Route();
        route.setCompany(company);
        route.setDriver(employeeService.getEmployeeByEgn("1234567890"));
        route.setDepartureAddress("Departure Address");
        route.setArrivalAddress("Arrival Address");
        route.setDepartureTime(LocalDateTime.now());
        route.setArrivalTime(LocalDateTime.now().plusHours(5));
        route.setDistance(330.0);
        route.setRoutePrice(BigDecimal.valueOf(550));
        route.setTransport(commodityTransport);
        List<MotorisedTransportVehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle);
        route.setVehicles(vehicles);

        //now save the transport in the database
        route = routeService.createRoute(route);

        assertTrue(route.getId() > 0, "Id missing.");
        assertEquals(route.getCompany().getId(), route.getDriver().getCompany().getId());
        assertEquals(route.getTransport().getCompany().getId(), route.getCompany().getId());


    }


    //обща сума на извършените превози
    @Transactional
    @Test
    void testToGetSumOfProfitForCompanyRoutes() {

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

        BigDecimal profit = routeService.getProfitOfRoutesByCompanyId(company.getId());

        assertEquals(BigDecimal.valueOf(5590), profit, "Actual profit differs from the expected value.");

    }


    //списък с шофьорите и колко превоза е осъществил всеки от тях в id и number of routes
    @Transactional
    @Test
    void testToGetAListOfDriversAndTheirTotalSumOfRoutes() {
        // 1A. Creating a company
        Company company = new Company();
        company.setCompanyName("MVI");
        company.setBudget(BigDecimal.valueOf(20000));
        company.setCreated(LocalDateTime.now());
        company = companyService.createCompany(company);

        // creating 5 employees
        List<Employee> employees = new ArrayList<>();


        Employee driver;
        for (int i = 0; i < 5; i++) {
            driver = new Employee();
            driver.setCompany(company);
            driver.setFirstName("employeeFirstName" + i);
            driver.setLastname("employeeLastName");
            driver.setEmail("employeeEmail");
            driver.setMonthlySalary(BigDecimal.valueOf(2000));
            driver.setPhoneNumber("088347594");
            driver.setAddress("employeeAddress");
            driver.setEgn("123456789" + i);
            driver.setDriversLicense(DriversLicense.C1);

            employees.add(driver);
        }

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

        //we update the company with the employees and the vehicle
        companyService.updateCompany(company);

        company = companyService.getCompanyByName("MVI");

        assertEquals(company.getCompanyName(), "MVI", "Company name doesn't match.");
        assertEquals(company.getEmployees().size(), 5, "Company employee list size doesn't match.");
        assertEquals(company.getMtvList().size(), 1, "Company mvt list size doesn't match.");

        //we now need the transport (passengers or commodity)
        //lets say we are driving around commodity
        Commodity commodityTransport = new Commodity();
        commodityTransport.setWeight(2550.);
        commodityTransport.setCompany(company);

        //save the transport in the database
        commodityTransport = (Commodity) transportService.createTransport(commodityTransport);

        //we create 2 routes with driver with egn 1234567890
        for (int i = 0; i < 2; i++) {
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

        //we create 6 routes with driver with egn 1234567891
        for (int i = 0; i < 6; i++) {
            Route route = new Route();
            route.setCompany(company);
            route.setDriver(employeeService.getEmployeeByEgn("1234567891"));
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

        //we create 4 routes with driver with egn 1234567893
        for (int i = 0; i < 4; i++) {
            Route route = new Route();
            route.setCompany(company);
            route.setDriver(employeeService.getEmployeeByEgn("1234567893"));
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

        //the rest of the drivers are not yet ready to drive ¯\_( ͡° ͜ʖ ͡°)_/¯
        Map<Integer, Integer> driverRoute;
        driverRoute = routeService.getDriverRouteDataByCompanyId(company.getId());

        assertEquals(2, driverRoute.get(employeeService.getEmployeeByEgn("1234567890").getId()));
        assertEquals(6, driverRoute.get(employeeService.getEmployeeByEgn("1234567891").getId()));
        assertEquals(4, driverRoute.get(employeeService.getEmployeeByEgn("1234567893").getId()));
    }


    //приходите на компанията от определените служители

    @Transactional
    @Test
    void testToFindTheAmountOfProfitFromEachEmployee() {
        // 1A. Creating a company
        Company company = new Company();
        company.setCompanyName("MVI");
        company.setBudget(BigDecimal.valueOf(20000));
        company.setCreated(LocalDateTime.now());
        company = companyService.createCompany(company);

        // creating 5 employees
        List<Employee> employees = new ArrayList<>();


        Employee driver;
        for (int i = 0; i < 5; i++) {
            driver = new Employee();
            driver.setCompany(company);
            driver.setFirstName("employeeFirstName" + i);
            driver.setLastname("employeeLastName");
            driver.setEmail("employeeEmail");
            driver.setMonthlySalary(BigDecimal.valueOf(750));
            driver.setPhoneNumber("088347594");
            driver.setAddress("employeeAddress");
            driver.setEgn("123456789" + i);
            driver.setDriversLicense(DriversLicense.C1);
            driver.setDateOfHiring(LocalDateTime.of(2023, 3 + i, 7, 8 + i, 0));

            employees.add(driver);
        }

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

        //we update the company with the employees and the vehicle
        companyService.updateCompany(company);

        company = companyService.getCompanyByName("MVI");

        assertEquals(company.getCompanyName(), "MVI", "Company name doesn't match.");
        assertEquals(company.getEmployees().size(), 5, "Company employee list size doesn't match.");
        assertEquals(company.getMtvList().size(), 1, "Company mvt list size doesn't match.");

        //we now need the transport (passengers or commodity)
        //lets say we are driving around commodity
        Commodity commodityTransport = new Commodity();
        commodityTransport.setWeight(2550.);
        commodityTransport.setCompany(company);

        //save the transport in the database
        commodityTransport = (Commodity) transportService.createTransport(commodityTransport);

        //we create 2 routes with driver with egn 1234567890
        for (int i = 0; i < 2; i++) {
            Route route = new Route();
            route.setCompany(company);
            route.setDriver(employeeService.getEmployeeByEgn("1234567890"));
            route.setDepartureAddress("Departure Address");
            route.setArrivalAddress("Arrival Address");
            route.setDepartureTime(LocalDateTime.of(2023, 7, 7, 8 + i, 0));
            route.setArrivalTime(LocalDateTime.of(2023, 7, 7, 13 + i, 0));
            route.setDistance(330.0 + i * 2);
            route.setRoutePrice(BigDecimal.valueOf(100 + i * 2));
            route.setTransport(commodityTransport);
            List<MotorisedTransportVehicle> vehicles = new ArrayList<>();
            vehicles.add(vehicle);
            route.setVehicles(vehicles);

            //then we create a client and add it to the route
            Client client = new Client();
            client.setName("clientName");
            client.setPhoneNumber("0883475940");
            client.setBudget(BigDecimal.valueOf(10000));

            //2.А. запазвам клиентите в базата
            client = clientService.createClient(client);

            boolean processedPayment = routeService.payRouteViaClient(client, route);

            assertTrue(processedPayment, "Payment Failed.");
            //now save the transport in the database
            routeService.createRoute(route);
        }

        //we create 6 routes with driver with egn 1234567891
        for (int i = 0; i < 6; i++) {
            Route route = new Route();
            route.setCompany(company);
            route.setDriver(employeeService.getEmployeeByEgn("1234567891"));
            route.setDepartureAddress("Departure Address");
            route.setArrivalAddress("Arrival Address");
            route.setDepartureTime(LocalDateTime.of(2023, 9, 7, 8 + i, 0));
            route.setArrivalTime(LocalDateTime.of(2023, 9, 7, 13 + i, 0));
            route.setDistance(330.0 + i * 2);
            route.setRoutePrice(BigDecimal.valueOf(1000 + i * 2));
            route.setTransport(commodityTransport);
            List<MotorisedTransportVehicle> vehicles = new ArrayList<>();
            vehicles.add(vehicle);
            route.setVehicles(vehicles);

            //then we create a client and add it to the route
            Client client = new Client();
            client.setName("clientName");
            client.setPhoneNumber("0883475940");
            client.setBudget(BigDecimal.valueOf(100000));

            //2.А. запазвам клиентите в базата
            client = clientService.createClient(client);

            boolean processedPayment = routeService.payRouteViaClient(client, route);

            assertTrue(processedPayment, "Payment Failed.");
            //now save the transport in the database
            routeService.createRoute(route);
        }

        //we create 4 routes with driver with egn 1234567893
        for (int i = 0; i < 4; i++) {
            Route route = new Route();
            route.setCompany(company);
            route.setDriver(employeeService.getEmployeeByEgn("1234567893"));
            route.setDepartureAddress("Departure Address");
            route.setArrivalAddress("Arrival Address");
            route.setDepartureTime(LocalDateTime.of(2023, 11, 7, 8 + i, 0));
            route.setArrivalTime(LocalDateTime.of(2023, 11, 7, 13 + i, 0));
            route.setDistance(330.0 + i * 2);
            route.setRoutePrice(BigDecimal.valueOf(1000 + i * 2));
            route.setTransport(commodityTransport);
            List<MotorisedTransportVehicle> vehicles = new ArrayList<>();
            vehicles.add(vehicle);
            route.setVehicles(vehicles);

            //then we create a client and add it to the route
            Client client = new Client();
            client.setName("clientName");
            client.setPhoneNumber("0883475940");
            client.setBudget(BigDecimal.valueOf(100000));

            //2.А. запазвам клиентите в базата
            client = clientService.createClient(client);

            boolean processedPayment = routeService.payRouteViaClient(client, route);

            assertTrue(processedPayment, "Payment Failed.");
            //now save the transport in the database
            routeService.createRoute(route);
            //now that I've simulated hiring drivers and them driving I can get the info I need to complete task 9 - ..., колко точно е приходът от всеки от шофьорите


            //I have 5 employees and I want to see their individual profit sum for the time which they have worked in my company
            //basically that would mean looking through the routes list and summing up each rout of which the driver is that employee
            //after I have that sum I'd need to calculate how much I have paid the driver based on how many months he has worked
            //then I subtract his salaries from his profits and I have a pretty good understanding of his individual profit for this company
            List<DriverProfitDTO> driverProfits = routeService.getProfitOfIndividualDriversByCompanyIdList(company.getId());

            assertFalse(driverProfits.isEmpty());
        }


    }

    @Transactional
    @Test
    void payRouteViaClientTest() {
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


        Route route = new Route();
        route.setCompany(company);
        route.setDriver(employeeService.getEmployeeByEgn("1234567890"));
        route.setDepartureAddress("Departure Address");
        route.setArrivalAddress("Arrival Address");
        route.setDepartureTime(LocalDateTime.now());
        route.setArrivalTime(LocalDateTime.now().plusHours(5));
        route.setDistance(330.0);
        route.setRoutePrice(BigDecimal.valueOf(550));
        route.setTransport(commodityTransport);
        List<MotorisedTransportVehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle);
        route.setVehicles(vehicles);

        //now save the transport in the database
        route = routeService.createRoute(route);

        assertTrue(route.getId() > 0, "Id missing.");
        assertEquals(route.getCompany().getId(), route.getDriver().getCompany().getId());
        assertEquals(route.getTransport().getCompany().getId(), route.getCompany().getId());

        //creating clients
        //създавам клиент
        Client client = new Client();
        client.setName("clientName");
        client.setPhoneNumber("0883475940");
        client.setBudget(BigDecimal.valueOf(600));

        //2.А. запазвам клиентите в базата
        client = clientService.createClient(client);

        boolean processedPayment = routeService.payRouteViaClient(client, route);

        assertTrue(processedPayment, "Payment Failed.");


        assertEquals(client.getBudget().toString(), "50");

    }


    //списък с шофьорите и колко превоза е осъществил всеки от тях в dto
    @Transactional
    @Test
    void testToGetDtoListOfDriversAndTheirTotalSumOfRoutes() {
        // 1A. Creating a company
        Company company = new Company();
        company.setCompanyName("MVI");
        company.setBudget(BigDecimal.valueOf(20000));
        company.setCreated(LocalDateTime.now());
        company = companyService.createCompany(company);

        // creating 5 employees
        List<Employee> employees = new ArrayList<>();


        Employee driver;
        for (int i = 0; i < 5; i++) {
            driver = new Employee();
            driver.setCompany(company);
            driver.setFirstName("employeeFirstName" + i);
            driver.setLastname("employeeLastName");
            driver.setEmail("employeeEmail");
            driver.setMonthlySalary(BigDecimal.valueOf(2000));
            driver.setPhoneNumber("088347594");
            driver.setAddress("employeeAddress");
            driver.setEgn("123456789" + i);
            driver.setDriversLicense(DriversLicense.C1);

            employees.add(driver);
        }

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

        //we update the company with the employees and the vehicle
        companyService.updateCompany(company);

        company = companyService.getCompanyByName("MVI");

        assertEquals(company.getCompanyName(), "MVI", "Company name doesn't match.");
        assertEquals(company.getEmployees().size(), 5, "Company employee list size doesn't match.");
        assertEquals(company.getMtvList().size(), 1, "Company mvt list size doesn't match.");

        //we now need the transport (passengers or commodity)
        //lets say we are driving around commodity
        Commodity commodityTransport = new Commodity();
        commodityTransport.setWeight(2550.);
        commodityTransport.setCompany(company);

        //save the transport in the database
        commodityTransport = (Commodity) transportService.createTransport(commodityTransport);

        //we create 2 routes with driver with egn 1234567890
        for (int i = 0; i < 2; i++) {
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

        //we create 6 routes with driver with egn 1234567891
        for (int i = 0; i < 6; i++) {
            Route route = new Route();
            route.setCompany(company);
            route.setDriver(employeeService.getEmployeeByEgn("1234567891"));
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

        //we create 4 routes with driver with egn 1234567893
        for (int i = 0; i < 4; i++) {
            Route route = new Route();
            route.setCompany(company);
            route.setDriver(employeeService.getEmployeeByEgn("1234567893"));
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

        //the rest of the drivers are not yet ready to drive ¯\_( ͡° ͜ʖ ͡°)_/¯
        List<DriverRouteDto> driverRouteDtoList = routeService.representDataForDriverRouteRelation(company.getId());

        assertEquals(3, driverRouteDtoList.size(), "List size not matched.");
    }


}
