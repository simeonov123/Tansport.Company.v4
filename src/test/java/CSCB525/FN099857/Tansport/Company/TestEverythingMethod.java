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
import CSCB525.FN099857.Tansport.Company.transport.Passengers;
import CSCB525.FN099857.Tansport.Company.transport.Transport;
import CSCB525.FN099857.Tansport.Company.transport.TransportService;
import CSCB525.FN099857.Tansport.Company.util.DriverProfitDTO;
import CSCB525.FN099857.Tansport.Company.util.DriverRouteDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootTest
public class TestEverythingMethod {


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


    //Create 10 companies
    //each company has 10 employees
    //each company has 10 vehicles
    //each company has 10 routes
    //each company has 10 clients
    //IMPORTANT ----------------------> running this test will create 10 Excel files on your desktop <3

    private static String generateUniqueEGN(String companyName, int index) {
        // Convert company name characters to numbers
        StringBuilder egnBuilder = new StringBuilder();
        for (char character : companyName.toCharArray()) {
            egnBuilder.append((int) character);
        }

        // Add loop indices (i and j) as numbers
        egnBuilder.append(index);

        // Ensure the length is 10 by padding with zeros or truncating
        String egn = egnBuilder.toString();
        if (egn.length() > 10) {
            return egn.substring(0, 10);
        } else if (egn.length() < 10) {
            return String.format("%-10s", egn).replace(' ', '0');
        } else {
            return egn;
        }
    }

    //This test simulates the functionality of the application.
    //Create companies
    //Employs people
    //Buys cars and trucks and stuff
    //Creates goods to transport
    //Creates potential Clients
    //Then if the clients can afford a Route a transaction is made and the Route is saved in the db, otherwise it's just not saved - so it's like an offer
    //Има всички функционалности които са описани в заданието на проекта.
    //Валидирани са данните с анотации
    //Документирани са методите
    @Test
    void testEverything() {
        //For debug purposes
        List<Company> companies = new ArrayList<>();
        List<MotorisedTransportVehicle> vehicles = new ArrayList<>();
        List<Employee> employees = new ArrayList<>();
        List<Route> routes = new ArrayList<>();

        int clientCanPayAndRouteIsCreated = 0;
        int clientCantPayAndRoutHasntBeenCreated = 0;

        Random random = new Random();

        //Creating 10 companies
        for (int i = 0; i < 10; i++) {
            //Create company
            Company company = new Company();

            // Generate a random letter for the last letter of the company name
            char randomLetter = (char) ('A' + new Random().nextInt(26));

            company.setCompanyName("MVI" + randomLetter);

            // Generate a random budget between 1,000,000 and 5,000,000
            BigDecimal randomBudget = BigDecimal.valueOf(1000000 + new Random().nextDouble() * (5000000 - 1000000));
            company.setBudget(randomBudget);

            company.setCreated(LocalDateTime.now());

            //For debug purposes
            List<Employee> employeesForCompanyList = new ArrayList<>();
            List<MotorisedTransportVehicle> vehiclesForCompanyList = new ArrayList<>();
            List<Transport> transportForCompanyList = new ArrayList<>();
            List<Client> clientsForCompanyList = new ArrayList<>();
            List<Route> routesForCompanyList = new ArrayList<>();

            company = companyService.createCompany(company);


            for (int j = 0; j < 10; j++) {
                Employee employee = new Employee();
                employee.setCompany(company);
                employee.setDateOfHiring(LocalDateTime.now());
                employee.setFirstName("EmployeeFirstName" + j);
                employee.setLastname("EmployeeLastName" + j);
                employee.setEmail("employeeEmail" + j);

                // Generate a random salary between 900 and 1450
                BigDecimal randomSalary = BigDecimal.valueOf(900 + new Random().nextDouble() * (1450 - 900));
                employee.setMonthlySalary(randomSalary);

                employee.setPhoneNumber("088347594" + j);
                employee.setAddress("employeeAddress" + j);

                String uniqueEGN = generateUniqueEGN(company.getCompanyName(), j);
                employee.setEgn(uniqueEGN);

                switch (j % 10) {
                    case 0, 5:
                        employee.setDriversLicense(DriversLicense.B);
                        break;
                    case 1, 4, 2, 9:
                        employee.setDriversLicense(DriversLicense.C);
                        break;
                    case 3:
                        employee.setDriversLicense(DriversLicense.A);
                        break;
                    case 6:
                        employee.setDriversLicense(DriversLicense.BE);
                        break;
                    case 7:
                        employee.setDriversLicense(DriversLicense.C1);
                        break;
                    case 8:
                        employee.setDriversLicense(DriversLicense.C1E);
                        break;
                    default:
                        System.out.println("Invalid option");
                }
                employeeService.createEmployee(employee);
                employeesForCompanyList.add(employee);
                employees.add(employee);


                //creating vehicles for each company
                MotorisedTransportVehicle vehicle = new MotorisedTransportVehicle();
                vehicle.setPurchasedOn(LocalDateTime.now());
                vehicle.setCompany(company);
                switch (j % 10) {
                    case 0, 5:
                        vehicle.setVehicleType(VehicleType.CAR);
                        vehicle.setModel(VehicleType.CAR + " " + company.getCompanyName());
                        vehicle.setRequiredDriversLicenseCategory(DriversLicense.B);

                        break;
                    case 1, 4, 2, 9:
                        vehicle.setVehicleType(VehicleType.TRUCK);
                        vehicle.setModel(VehicleType.TRUCK + " " + company.getCompanyName());
                        vehicle.setRequiredDriversLicenseCategory(DriversLicense.C);

                        break;
                    case 3:
                        vehicle.setVehicleType(VehicleType.MOTORCYCLE);
                        vehicle.setModel(VehicleType.MOTORCYCLE + " " + company.getCompanyName());
                        vehicle.setRequiredDriversLicenseCategory(DriversLicense.A);

                        break;
                    case 6:
                        vehicle.setVehicleType(VehicleType.GARBAGE_TRUCK);
                        vehicle.setModel(VehicleType.GARBAGE_TRUCK + " " + company.getCompanyName());
                        vehicle.setRequiredDriversLicenseCategory(DriversLicense.BE);

                        break;
                    case 7:
                        vehicle.setVehicleType(VehicleType.TRAILER);
                        vehicle.setModel(VehicleType.TRAILER + " " + company.getCompanyName());
                        vehicle.setRequiredDriversLicenseCategory(DriversLicense.C1);

                        break;
                    case 8:
                        vehicle.setVehicleType(VehicleType.CONSTRUCTION_VEHICLE);
                        vehicle.setModel(VehicleType.CONSTRUCTION_VEHICLE + " " + company.getCompanyName());
                        vehicle.setRequiredDriversLicenseCategory(DriversLicense.C1E);

                        break;
                    default:
                        System.out.println("Invalid option");
                }
                vehicle = mtvService.createVehicle(vehicle);
                vehiclesForCompanyList.add(vehicle);
                vehicles.add(vehicle);


                //creating transports and routes and clients

                if (j < 5) {
                    Client client = new Client();
                    client.setName("Client Name " + i * j + i + j + 1);
                    client.setBudget(BigDecimal.valueOf(random.nextDouble(25000 - 1500 + 1) + 1500));
                    client.setPhoneNumber("088347594" + j);

                    client = clientService.createClient(client);
                    clientsForCompanyList.add(client);

                    List<Company> clientsCompany = new ArrayList<>();
                    clientsCompany.add(company);
                    client.setCompanies(clientsCompany);

                    clientsForCompanyList.add(client);

                    Passengers passengers = new Passengers();
                    //random number between 10 and 50 for passengers
                    passengers.setNumberOfPassengers(random.nextInt(50 - 10 + 1) + 10);
                    passengers.setCompany(company);
                    passengers = (Passengers) transportService.createTransport(passengers);

                    transportForCompanyList.add(passengers);

                    clientsForCompanyList.add(client);

                    Route route = new Route();
                    route.setCompany(company);
                    route.setClient(client);
                    route.setTransport(passengers);
                    route.setVehicles(vehiclesForCompanyList);
                    route.setDriver(employee);
                    route.setDistance(random.nextDouble(8000 - 100 + 1) + 100);
                    route.setDepartureAddress("Pernik, st. No " + j + i);
                    route.setArrivalAddress("Viena, st. No " + j * i + 5);
                    LocalDateTime departureTime = LocalDateTime.now().plusDays(random.nextInt(50 - 10 + 1) + 10);
                    route.setDepartureTime(departureTime);
                    route.setArrivalTime(departureTime.plusHours(random.nextInt(50 - 10 + 1) + 10));
                    route.setRoutePrice(BigDecimal.valueOf(random.nextDouble(25000 - 1500 + 1) + 1500));

                    boolean processedPayment = routeService.payRouteViaClient(client, route);

                    if (processedPayment) {
                        route = routeService.createRoute(route);
                        routes.add(route);
                        routesForCompanyList.add(route);
                        clientCanPayAndRouteIsCreated++;
                    } else {
                        clientCantPayAndRoutHasntBeenCreated++;
                    }


                } else {
                    Client client = new Client();
                    client.setName("Client Name " + i * j + i + j + 1);
                    client.setBudget(BigDecimal.valueOf(random.nextDouble(25000 - 1500 + 1) + 1500));
                    client.setPhoneNumber("088347594" + j);

                    client = clientService.createClient(client);
                    clientsForCompanyList.add(client);

                    List<Company> clientsCompany = new ArrayList<>();
                    clientsCompany.add(company);
                    client.setCompanies(clientsCompany);


                    Commodity commodity = new Commodity();
                    //random number between 250 and 5000 for weight
                    commodity.setWeight(random.nextInt(5000 - 250 + 1) + 250);
                    commodity.setCompany(company);
                    commodity = (Commodity) transportService.createTransport(commodity);

                    transportForCompanyList.add(commodity);

                    Route route = new Route();
                    route.setCompany(company);
                    route.setClient(client);
                    route.setTransport(commodity);
                    route.setVehicles(vehiclesForCompanyList);
                    route.setDriver(employee);
                    route.setDistance(random.nextDouble(8000 - 100 + 1) + 100);
                    route.setDepartureAddress("Pernik, st. No " + j + i);
                    route.setArrivalAddress("Viena, st. No " + j * i + 5);
                    LocalDateTime departureTime = LocalDateTime.now().plusDays(random.nextInt(50 - 10 + 1) + 10);
                    route.setDepartureTime(departureTime);
                    route.setArrivalTime(departureTime.plusHours(random.nextInt(50 - 10 + 1) + 10));
                    route.setRoutePrice(BigDecimal.valueOf(random.nextDouble(25000 - 1500 + 1) + 1500));

                    boolean processedPayment = routeService.payRouteViaClient(client, route);

                    if (processedPayment) {
                        route = routeService.createRoute(route);
                        routes.add(route);
                        routesForCompanyList.add(route);
                        clientCanPayAndRouteIsCreated++;
                    } else {
                        clientCantPayAndRoutHasntBeenCreated++;
                    }

                }


            }
            company.setClientsList(clientsForCompanyList);
            company.setEmployees(employeesForCompanyList);
            company.setMtvList(vehiclesForCompanyList);
            company.setRoutes(routesForCompanyList);
            companyService.updateCompany(company);
            companies.add(company);


        }


        for (Company company : companies) {
            System.out.println(company);

            List<DriverProfitDTO> driverProfits = routeService.getProfitOfIndividualDriversByCompanyIdList(company.getId());
            for (DriverProfitDTO driverProfitDTO : driverProfits) {
                System.out.println(driverProfitDTO);
            }

            List<DriverRouteDto> driverRouteDtoList = routeService.representDataForDriverRouteRelation(company.getId());
            for (DriverRouteDto driverRouteDto : driverRouteDtoList) {
                System.out.println(driverRouteDto);
            }

            BigDecimal profit = routeService.getProfitOfRoutesByCompanyId(company.getId());
            System.out.println("Profit = " + profit);

            System.out.println(clientCanPayAndRouteIsCreated + " clients payed ╰(*°▽°*)╯");

            System.out.println(clientCantPayAndRoutHasntBeenCreated + " clients couldn't pay (´。＿。｀)");

            try {
                System.out.println("Saving route data on your desktop...");
                routeService.exportRoutesToExcel(company.getId());
                System.out.println("saved!");
            } catch (Exception e) {
                System.out.println("Something went wrong saving route data to your desktop.. oh well :)");
                System.out.println("¯\\_(ツ)_/¯");
            }
        }

    }
}
