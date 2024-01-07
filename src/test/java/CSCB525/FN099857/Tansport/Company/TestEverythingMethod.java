package CSCB525.FN099857.Tansport.Company;

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
import CSCB525.FN099857.Tansport.Company.transport.TransportService;
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


    //Create 5 companies
    //each company has 10 employees
    //each company has 10 vehicles
    //each company has 10 routes
    //each company has 10 clients

    @Test
    void testEverything() {
        List<Company> companies = new ArrayList<>();
        List<MotorisedTransportVehicle> vehicles = new ArrayList<>();
        List<Employee> employees = new ArrayList<>();
        List<Route> routes = new ArrayList<>();


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

            List<Employee> employeesForCompanyList = new ArrayList<>();
            List<MotorisedTransportVehicle> vehiclesForCompanyList = new ArrayList<>();
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
                employeesForCompanyList.add(employee);


                //creating vehicles for each company
                MotorisedTransportVehicle vehicle = new MotorisedTransportVehicle();
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

                vehicle.setCompany(company);
                vehicle.setPurchasedOn(LocalDateTime.now());
                vehiclesForCompanyList.add(vehicle);
                vehicles.add(vehicle);


            }

            company.setEmployees(employeesForCompanyList);
            company.setMtvList(vehiclesForCompanyList);
            company = companyService.createCompany(company);
            companies.add(company);
        }


        System.out.println("ASDASD");


    }


    private static String generateUniqueEGN(String companyName, int index) {
        // Convert company name characters to numbers
        StringBuilder egnBuilder = new StringBuilder();
        for (char character : companyName.toCharArray()) {
            egnBuilder.append((int) character);
        }

        // Add loop indices (i and j) as numbers
        egnBuilder.append(index);

        // If needed, add more information (e.g., j)
        // egnBuilder.append(j);

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
}
