package CSCB525.FN099857.Tansport.Company.mtv;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing operations related to motorized transport vehicles (MTVs).
 */
@Service
public class MotorisedTransportVehicleService {

    private final MotorisedTransportVehicleRepository motorisedTransportVehicleRepository;

    /**
     * Constructs a MotorisedTransportVehicleService with the provided repository.
     *
     * @param motorisedTransportVehicleRepository The repository for accessing and managing MTV entities.
     */
    public MotorisedTransportVehicleService(MotorisedTransportVehicleRepository motorisedTransportVehicleRepository) {
        this.motorisedTransportVehicleRepository = motorisedTransportVehicleRepository;
    }

    /**
     * Updates the information of a motorized transport vehicle.
     *
     * @param vehicleToBeUpdated The motorized transport vehicle with updated information.
     */
    public void updateVehicle(@Valid MotorisedTransportVehicle vehicleToBeUpdated) {
        motorisedTransportVehicleRepository.save(vehicleToBeUpdated);
    }

    /**
     * Retrieves a motorized transport vehicle by its model.
     *
     * @param model The model of the motorized transport vehicle.
     * @return The motorized transport vehicle with the specified model, or null if not found.
     */
    public MotorisedTransportVehicle getVehicleByModel(@NotBlank String model) {
        return motorisedTransportVehicleRepository.findByModel(model);
    }

    /**
     * Retrieves a list of motorized transport vehicles associated with a specific company.
     *
     * @param id The unique identifier of the company.
     * @return A list of motorized transport vehicles associated with the specified company.
     */
    @Transactional
    public List<MotorisedTransportVehicle> getAllVehiclesByCompanyId(@NotNull Integer id) {
        return motorisedTransportVehicleRepository.findAllByCompanyId(id);
    }

    /**
     * Deletes a list of motorized transport vehicles.
     *
     * @param vehiclesToBeDeleted The list of motorized transport vehicles to be deleted.
     */
    @Transactional
    public void deleteVehicles(List<@Valid MotorisedTransportVehicle> vehiclesToBeDeleted) {
        List<Integer> ids = vehiclesToBeDeleted.stream()
                .map(MotorisedTransportVehicle::getId)
                .collect(Collectors.toList());

        motorisedTransportVehicleRepository.deleteAllByIdIn(ids);
    }

    /**
     * Creates a new motorized transport vehicle and saves it to the database.
     * <p>
     * This method takes a MotorisedTransportVehicle object as input, representing the details
     * of the vehicle, and persists it in the underlying database using the repository.
     *
     * @param vehicle The MotorisedTransportVehicle object containing details of the vehicle to be created.
     * @return The created MotorisedTransportVehicle object, including any automatically generated identifiers.
     * @throws IllegalArgumentException If the provided vehicle object is null.
     */
    public MotorisedTransportVehicle createVehicle(MotorisedTransportVehicle vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle object cannot be null");
        }

        // Save the vehicle to the database
        return motorisedTransportVehicleRepository.save(vehicle);
    }


    /**
     * Deletes a motorized transport vehicle from the database based on its unique identifier.
     * <p>
     * This method takes the identifier (ID) of a motorized transport vehicle as input,
     * searches for the corresponding vehicle in the database, and deletes it if found.
     *
     * @param id The unique identifier of the motorized transport vehicle to be deleted.
     * @throws EmptyResultDataAccessException If no vehicle with the specified ID is found in the database.
     *                                        This exception is thrown by Spring Data when attempting to delete
     *                                        a non-existing entity.
     * @throws IllegalArgumentException       If the provided ID is null.
     */
    public void deleteVehicleById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Vehicle ID cannot be null");
        }

        // Delete the vehicle from the database
        motorisedTransportVehicleRepository.deleteById(id);
    }
}