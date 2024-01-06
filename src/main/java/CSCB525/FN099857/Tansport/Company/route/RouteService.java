package CSCB525.FN099857.Tansport.Company.route;

import CSCB525.FN099857.Tansport.Company.client.Client;
import CSCB525.FN099857.Tansport.Company.client.ClientService;
import CSCB525.FN099857.Tansport.Company.copany.Company;
import CSCB525.FN099857.Tansport.Company.copany.CompanyService;
import CSCB525.FN099857.Tansport.Company.employee.Employee;
import CSCB525.FN099857.Tansport.Company.employee.EmployeeService;
import CSCB525.FN099857.Tansport.Company.util.DriverProfitDTO;
import CSCB525.FN099857.Tansport.Company.util.DriverRouteDto;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service class for managing Route entities.
 */
@Service
public class RouteService {

    private final RouteRepository routeRepository;
    private final CompanyService companyService;
    private final EmployeeService employeeService;
    private final ClientService clientService;

    /**
     * Constructor for RouteService.
     *
     * @param routeRepository   The repository for Route entities.
     * @param companyService    The service for managing Company entities.
     * @param employeeService   The service for managing Employee entities.
     * @param clientService     The service for managing Client entities.
     */
    public RouteService(RouteRepository routeRepository, CompanyService companyService, EmployeeService employeeService, ClientService clientService) {
        this.routeRepository = routeRepository;
        this.companyService = companyService;
        this.employeeService = employeeService;
        this.clientService = clientService;
    }

    /**
     * Creates a new route.
     *
     * @param routeToBeCreated The route to be created.
     * @return The created route.
     */
    public Route createRoute(@Valid Route routeToBeCreated) {
        return routeRepository.save(routeToBeCreated);
    }

    /**
     * Updates an existing route.
     *
     * @param route The route to be updated.
     * @return The updated route.
     */
    public Route updateRoute(@Valid Route route) {
        return routeRepository.save(route);
    }

    /**
     * Processes payment for a route via a client.
     *
     * @param client The client making the payment.
     * @param route  The route for which the payment is made.
     * @return True if the payment was successful, false otherwise.
     */
    @Transactional
    public boolean payRouteViaClient(@Valid Client client, @Valid Route route) {
        // Check if the client can afford the route
        if (client.getBudget().compareTo(route.getRoutePrice()) < 0) {
            return false;
        }

        // Subtract money from the client and update the company budget
        client.setBudget(client.getBudget().subtract(route.getRoutePrice()));
        Company company = companyService.getCompanyById(route.getCompany().getId());
        company.setBudget(company.getBudget().add(route.getRoutePrice()));

        // Update client and company associations
        updateClientAndCompanyAssociations(client, company);

        // Update the route with the client
        route.setClient(client);
        updateRoute(route);

        return true;
    }

    // Helper method to update client and company associations
    private void updateClientAndCompanyAssociations(Client client, Company company) {
        if (client.getCompanies() == null) {
            client.setCompanies(new ArrayList<>());
        }
        client.getCompanies().add(company);
        clientService.updateClient(client);

        List<Client> companyClientList = company.getClientsList();
        if (companyClientList == null) {
            companyClientList = new ArrayList<>();
        }
        companyClientList.add(client);
        company.setClientsList(companyClientList);
        companyService.updateCompany(company);
    }

    /**
     * Retrieves all routes associated with a specific company.
     *
     * @param companyId The ID of the company.
     * @return A list of routes associated with the specified company.
     */
    public List<Route> getAllRoutesByCompanyId(@Positive int companyId) {
        return routeRepository.findAllByCompanyId(companyId);
    }

    /**
     * Calculates the total profit of routes for a specific company.
     *
     * @param id The ID of the company.
     * @return The total profit of routes for the specified company.
     */
    public BigDecimal getProfitOfRoutesByCompanyId(@Positive Integer id) {
        List<Route> routeList = getAllRoutesByCompanyId(id);

        return routeList.stream()
                .map(Route::getRoutePrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Retrieves a map of driver IDs and the count of routes they have for a specific company.
     *
     * @param id The ID of the company.
     * @return A map of driver IDs and their route counts.
     */
    public Map<Integer, Integer> getDriverRouteDataByCompanyId(@Positive Integer id) {
        List<Route> allRoutes = routeRepository.findAllByCompanyId(id);
        Map<Integer, Integer> driverRouteCountMap = new HashMap<>();

        for (Route route : allRoutes) {
            Integer driverId = route.getDriver().getId();
            driverRouteCountMap.put(driverId, driverRouteCountMap.getOrDefault(driverId, 0) + 1);
        }

        return driverRouteCountMap;
    }

    /**
     * Represents data for driver-route relationships for a specific company.
     *
     * @param companyId The ID of the company.
     * @return A list of DriverRouteDto objects representing the relationship between drivers and routes.
     */
    public List<DriverRouteDto> representDataForDriverRouteRelation(@Positive int companyId) {
        Map<Integer, Integer> driverRouteCountMap = getDriverRouteDataByCompanyId(companyId);
        List<DriverRouteDto> driverRouteDtos = new ArrayList<>();

        for (Map.Entry<Integer, Integer> entry : driverRouteCountMap.entrySet()) {
            Integer employeeId = entry.getKey();
            int numberOfRoutes = entry.getValue();
            Employee employee = employeeService.getEmployeeById(employeeId);
            DriverRouteDto driverRouteDto = new DriverRouteDto(employee, numberOfRoutes);
            driverRouteDtos.add(driverRouteDto);
        }

        return driverRouteDtos;
    }

    /**
     * Calculates the profit of routes for a specific company within a given period of time.
     *
     * @param id    The ID of the company.
     * @param start The start date and time of the period.
     * @param end   The end date and time of the period.
     * @return The profit of routes for the specified company within the specified time period.
     */
    public BigDecimal getProfitOfPeriodOfTimeByCompanyId(@Positive int id,
                                                         @NotNull @PastOrPresent LocalDateTime start,
                                                         @NotNull @PastOrPresent LocalDateTime end) {
        List<Route> routesInPeriod = routeRepository.findAllByCompanyIdAndDepartureTimeBetween(id, start, end);
        BigDecimal totalRoutePrice = routesInPeriod.stream()
                .map(Route::getRoutePrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<Employee> employees = employeeService.getAllEmployeesByCompanyId(id);
        BigDecimal totalSalaryCost = employees.stream()
                .map(employee -> calculateSalaryForPeriod(employee, start, end))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return totalRoutePrice.subtract(totalSalaryCost);
    }

    // Helper method to calculate the salary for an employee during a specified period
    private BigDecimal calculateSalaryForPeriod(Employee employee, LocalDateTime start, LocalDateTime end) {
        // Your logic for calculating the salary based on employee's salary and working hours
        // For simplicity, let's assume a fixed monthly salary without considering working hours
        return employee.getMonthlySalary();
    }

    /**
     * Retrieves a map of driver IDs and their net profits for a specific company.
     *
     * @param id The ID of the company.
     * @return A map of driver IDs and their net profits.
     */
    public Map<Integer, BigDecimal> getProfitOfIndividualDriversByCompanyId(@Positive Integer id) {
        List<Route> allRoutes = routeRepository.findAllByCompanyId(id);
        Map<Integer, BigDecimal> driverProfitMap = allRoutes.stream()
                .collect(Collectors.groupingBy(
                        route -> route.getDriver().getId(),
                        Collectors.mapping(Route::getRoutePrice, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))
                ));

        driverProfitMap.replaceAll((driverId, totalProfit) -> {
            Employee driver = employeeService.getEmployeeById(driverId);
            long monthsWorked = ChronoUnit.MONTHS.between(driver.getDateOfHiring(), LocalDateTime.now());
            BigDecimal totalSalaryCost = driver.getMonthlySalary().multiply(BigDecimal.valueOf(monthsWorked));
            return totalProfit.subtract(totalSalaryCost);
        });

        return driverProfitMap;
    }

    /**
     * Retrieves a list of DriverProfitDTO objects representing the net profits of individual drivers for a specific company.
     *
     * @param id The ID of the company.
     * @return A list of DriverProfitDTO objects.
     */
    public List<DriverProfitDTO> getProfitOfIndividualDriversByCompanyIdList(@Positive Integer id) {
        Map<Integer, BigDecimal> driverProfitMap = getProfitOfIndividualDriversByCompanyId(id);
        return driverProfitMap.entrySet().stream()
                .map(entry -> {
                    Employee employee = employeeService.getEmployeeById(entry.getKey());
                    return new DriverProfitDTO(employee, entry.getValue());
                })
                .collect(Collectors.toList());
    }
}
