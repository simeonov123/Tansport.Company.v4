package CSCB525.FN099857.Tansport.Company.route;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for managing Route entities in the database.
 */
@Repository
public interface RouteRepository extends JpaRepository<Route, Integer> {

    /**
     * Retrieves a list of routes associated with a specific company.
     *
     * @param companyId The ID of the company.
     * @return A list of routes associated with the specified company.
     */
    List<Route> findAllByCompanyId(int companyId);

    /**
     * Retrieves a list of routes associated with a specific company and with departure times between the specified start and end times.
     *
     * @param companyId The ID of the company.
     * @param start     The start date and time.
     * @param end       The end date and time.
     * @return A list of routes associated with the specified company and within the specified time range.
     */
    @Query("SELECT r FROM Route r WHERE r.company.id = :companyId AND r.departureTime BETWEEN :start AND :end")
    List<Route> findAllByCompanyIdAndDepartureTimeBetween(
            @Param("companyId") Integer companyId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

}
