package CSCB525.FN099857.Tansport.Company.mtv;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
}