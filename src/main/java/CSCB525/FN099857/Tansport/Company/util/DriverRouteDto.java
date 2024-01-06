package CSCB525.FN099857.Tansport.Company.util;

import CSCB525.FN099857.Tansport.Company.employee.Employee;

/**
 * A Data Transfer Object (DTO) representing the route information for a driver.
 * It encapsulates an Employee entity and the corresponding number of routes assigned to the driver.
 */
public class DriverRouteDto {
    private Employee employee;     // The associated employee
    private int numberOfRoutes;    // The number of routes assigned to the driver

    /**
     * Constructs a new instance of DriverRouteDto with default values.
     */
    public DriverRouteDto() {
    }

    /**
     * Constructs a new instance of DriverRouteDto with the specified Employee and number of routes.
     *
     * @param employee       The associated Employee entity.
     * @param numberOfRoutes The number of routes assigned to the driver.
     */
    public DriverRouteDto(Employee employee, int numberOfRoutes) {
        this.employee = employee;
        this.numberOfRoutes = numberOfRoutes;
    }

    /**
     * Gets the associated Employee entity.
     *
     * @return The Employee entity associated with the DTO.
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     * Sets the associated Employee entity.
     *
     * @param employee The Employee entity to be set.
     */
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    /**
     * Gets the number of routes assigned to the driver.
     *
     * @return The number of routes as an integer.
     */
    public int getNumberOfRoutes() {
        return numberOfRoutes;
    }

    /**
     * Sets the number of routes assigned to the driver.
     *
     * @param numberOfRoutes The number of routes to be set.
     */
    public void setNumberOfRoutes(int numberOfRoutes) {
        this.numberOfRoutes = numberOfRoutes;
    }

    /**
     * Overrides the default toString method to provide a customized string representation of the DriverRouteDto.
     *
     * @return A string representation of the DriverRouteDto object.
     */
    @Override
    public String toString() {
        return "DriverRouteDto{" +
                "employee=" + employee.getEgn() +
                ", numberOfRoutes=" + numberOfRoutes +
                '}';
    }
}
