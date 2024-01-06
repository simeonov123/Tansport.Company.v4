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
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner demo(CompanyService companyService, EmployeeService employeeService, ClientService clientService, MotorisedTransportVehicleService motorisedTransportVehicleService) {
        return args -> {

            //1 задача. Въвеждане, редактиране и изтриване на транспортна компания, която извършва
            //транспортни услуги и в която са наети служители

            //1А.създаваме компания
            Company company = new Company();
            company.setCompanyName("MVI");
            company.setBudget(BigDecimal.valueOf(20000));
            company.setCreated(LocalDateTime.now());
            companyService.createCompany(company);

            //1Б. създавам 5 служители и ги добавям във фирмата (редактиране дефакто) - от 4-та задача. Въвеждане, редактиране и изтриване на служителите на компанията - така въвеждам и изпълнявам задача 4.А.
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

            //1.В. обновявам компанията със запазените клиенти
            company.setClientsList(clients);
            companyService.updateCompany(company);


            //3. Въвеждане, редактиране и изтриване на превозните средства, които са собственост на
            //компанията

            //3.A Създаване на превозни средства и добавяне като собственост в компанията
            List<MotorisedTransportVehicle> vehicles = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                MotorisedTransportVehicle vehicle = new MotorisedTransportVehicle();
                vehicle.setCompany(company);
                vehicle.setModel("model" + i);
                vehicle.setPurchasedOn(LocalDateTime.now());
                switch (i) {
                    case 0:
                        vehicle.setVehicleType(VehicleType.BUS);
                        vehicle.setRequiredDriversLicenseCategory(DriversLicense.C1E);
                        break;
                    case 1:
                        vehicle.setVehicleType(VehicleType.FORKLIFT);
                        vehicle.setRequiredDriversLicenseCategory(DriversLicense.TTM);
                        break;
                    case 2:
                        vehicle.setVehicleType(VehicleType.CRANE);
                        vehicle.setRequiredDriversLicenseCategory(DriversLicense.TTM);
                        break;
                    case 3:
                        vehicle.setVehicleType(VehicleType.CONSTRUCTION_VEHICLE);
                        vehicle.setRequiredDriversLicenseCategory(DriversLicense.TTM);
                        break;
                    case 4:
                        vehicle.setVehicleType(VehicleType.CAR);
                        vehicle.setRequiredDriversLicenseCategory(DriversLicense.B);
                        break;
                    default:
                        System.out.println("Invalid option");
                }

                vehicles.add(vehicle);

            }

            //Запазвам ги в бд
            company.setMtvList(vehicles);
            companyService.updateCompany(company);


            //3.Б Редактиране на превозните средства които са собственост на компанията
            for (MotorisedTransportVehicle vehicle : vehicles) {
                MotorisedTransportVehicle vehicleToBeUpdated = motorisedTransportVehicleService.getVehicleByModel(vehicle.getModel());
                vehicleToBeUpdated.setModel("alteredModel");
                motorisedTransportVehicleService.updateVehicle(vehicleToBeUpdated);
            }

            //3.Г. Изтриване на превозните средства
            List<MotorisedTransportVehicle> vehiclesToBeDeleted = motorisedTransportVehicleService.getAllVehiclesByCompanyId(company.getId());
            motorisedTransportVehicleService.deleteVehicles(vehiclesToBeDeleted);


            //4 задача. Въвеждане, редактиране и изтриване на служителите на компанията.
            //По горе въвеждам служители когато създавам компанията, на същия принцип е - реално служителят като обект в програмата се запазва в базата чрез редактиране на Company а не чрез метод в EmployeeService (Въпреки че ако това се цели мога да напиша метод в който да се изисква като аргумент името или id-то на компанията и на база това да създава employee към съответната компания при положителен резултат от проверката дали такава съществува с такова id/име, просто нямам време защото правя проекта в последният момент и целя да изпълня задачите така както са зададени и така както са написани изпълнявам условието, така че ще решавам другите задачи)

            //4.Б. Редактиране на служителя
            //gotta get a hold of these employees via the company id (cuz in this case I have the id)
            List<Employee> employeeList = employeeService.getAllEmployeesByCompanyId(company.getId());

            //update the employees via a func or something
            int counter = 0;
            for (Employee employee : employeeList) {
                employee.setFirstName("alteredName" + counter);
                employeeService.updateEmployee(employee);
                counter++;
            }

            //4.В. Изтриване на служители от компанията
            //Ще изтрия двама само, защото долу метода, който трие компанията ще изтрие и останалите
            //I'll delete mister employee with first name "alteredName0" and mister employee with first name "alteredName1"
            for (int i = 0; i < 5; i++) {

                if (employeeList.get(i).getId().equals(1) || employeeList.get(i).getId().equals(2)) {
                    employeeService.deleteEmployeeByEgn(employeeList.get(i).getEgn()); //since the egn is unique I might as well query by that
                }
            }


            //5. Възможност за записване на данни за превозите (дестинация, товар, цена и др.)
            //So basically to create a route with stuff like distance, price and stuff

            Route route = new Route();


            //1.Г. Изтриване на компанията и на служителите заедно с нея
            companyService.deleteCompany(company);

            //2.Б. Редактиране на клиентите
            counter = 0;
            for (Client client : clients) {
                Client clientToBeUpdated = clientService.getClientByName(client.getName());
                clientToBeUpdated.setName("alteredName" + counter);
                clientService.updateClient(clientToBeUpdated);
                counter++;
            }

            //2.В. изтриване на клиентите
            counter = 0;
            for (Client client : clients) {
                Client clientToBeDeleted = clientService.getClientByName("alteredName" + counter);
                clientService.deleteClient(clientToBeDeleted);
                counter++;
            }


        };
    }
}