package CSCB525.FN099857.Tansport.Company.util;

import CSCB525.FN099857.Tansport.Company.employee.Employee;

import java.math.BigDecimal;

/**
 * A Data Transfer Object (DTO) representing the profit information for a driver.
 * It encapsulates an Employee entity and the corresponding profit calculated.
 */
public class DriverProfitDTO {
    private Employee employee;  // The associated employee
    private BigDecimal profit;  // The calculated profit for the driver

    /**
     * Constructs a new instance of DriverProfitDTO with default values.
     */
    public DriverProfitDTO() {
    }

    /**
     * Constructs a new instance of DriverProfitDTO with the specified Employee and profit.
     *
     * @param employee The associated Employee entity.
     * @param profit   The calculated profit for the driver.
     */
    public DriverProfitDTO(Employee employee, BigDecimal profit) {
        this.employee = employee;
        this.profit = profit;
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
     * Gets the calculated profit for the driver.
     *
     * @return The calculated profit as a BigDecimal.
     */
    public BigDecimal getProfit() {
        return profit;
    }

    /**
     * Sets the calculated profit for the driver.
     *
     * @param profit The calculated profit to be set.
     */
    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    /**
     * Overrides the default toString method to provide a customized string representation of the DriverProfitDTO.
     *
     * @return A string representation of the DriverProfitDTO object.
     */
    @Override
    public String toString() {
        return "DriverProfitDTO{" +
                "employee=" + employee.getEgn() +
                ", profit=" + profit +
                '}';
    }
}
