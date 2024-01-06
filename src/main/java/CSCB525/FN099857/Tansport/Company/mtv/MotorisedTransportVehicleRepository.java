package CSCB525.FN099857.Tansport.Company.mtv;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for accessing and managing motorized transport vehicle (MTV) entities in the database.
 */
@Repository
public interface MotorisedTransportVehicleRepository extends JpaRepository<MotorisedTransportVehicle, Integer> {

    /**
     * Retrieves a motorized transport vehicle by its model.
     *
     * @param model The model of the motorized transport vehicle.
     * @return The motorized transport vehicle with the specified model, or null if not found.
     */
    MotorisedTransportVehicle findByModel(String model);

    /**
     * Retrieves a list of motorized transport vehicles associated with a specific company.
     *
     * @param id The unique identifier of the company.
     * @return A list of motorized transport vehicles associated with the specified company.
     */
    List<MotorisedTransportVehicle> findAllByCompanyId(Integer id);

    /**
     * Deletes motorized transport vehicles by their unique identifiers.
     *
     * @param ids The list of unique identifiers of the motorized transport vehicles to be deleted.
     */
    @Modifying
    @Query("DELETE FROM MotorisedTransportVehicle WHERE id IN :ids")
    void deleteAllByIdIn(@Param("ids") List<Integer> ids);
}