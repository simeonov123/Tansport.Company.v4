package CSCB525.FN099857.Tansport.Company.transport;

import jakarta.validation.Valid;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

/**
 * Service class for managing Transport entities. Provides methods for creating and managing transportation entities.
 */
@Service
public class TransportService {

    private final TransportRepository transportRepository;

    /**
     * Constructs a new instance of TransportService.
     *
     * @param transportRepository The repository for accessing and managing Transport entities.
     */
    public TransportService(@Valid TransportRepository transportRepository) {
        this.transportRepository = transportRepository;
    }

    /**
     * Creates a new transport entity, specifically a Commodity transport, and saves it to the data store.
     *
     * @param transport The Commodity or Passengers transport entity to be created and saved.
     * @return The created transport entity.
     */
    public Transport createTransport(@Valid Transport transport) {
        return transportRepository.save(transport);
    }

    /**
     * Updates an existing transport entity in the data store.
     *
     * This method takes an updated transport entity and saves it to the database. The provided transport entity
     * should have a valid ID that corresponds to existing transport in the database.
     *
     * @param updatedTransport The updated transport entity to be saved.
     * @return The updated transport entity.
     * @throws IllegalArgumentException If the provided transport entity has a null ID.
     */
    public Transport updateTransport(@Valid Transport updatedTransport) {
        if (updatedTransport.getId() == null) {
            throw new IllegalArgumentException("Transport ID cannot be null for update");
        }

        // Check if the transport with the given ID exists in the database
        if (!transportRepository.existsById(updatedTransport.getId())) {
            throw new IllegalArgumentException("No transport found with ID: " + updatedTransport.getId());
        }

        // Save the updated transport entity to the database
        return transportRepository.save(updatedTransport);
    }

    /**
     * Deletes a transport entity from the data store based on its unique identifier.
     *
     * This method takes the identifier (ID) of a transport entity as input, searches for the corresponding
     * transport in the database, and deletes it if found.
     *
     * @param transportId The unique identifier of the transport entity to be deleted.
     * @throws EmptyResultDataAccessException If no transport with the specified ID is found in the database.
     *                                      This exception is thrown by Spring Data when attempting to delete
     *                                      a non-existing entity.
     * @throws IllegalArgumentException      If the provided transport ID is null.
     */
    public void deleteTransportById(Integer transportId) {
        if (transportId == null) {
            throw new IllegalArgumentException("Transport ID cannot be null for deletion");
        }

        // Delete the transport from the database
        transportRepository.deleteById(transportId);
    }

    /**
     * Retrieves a transport entity from the data store based on its unique identifier.
     *
     * This method takes the identifier (ID) of a transport entity as input, searches for the corresponding
     * transport in the database, and returns it if found.
     *
     * @param transportId The unique identifier of the transport entity to be retrieved.
     * @return The transport entity with the specified ID, or null if not found.
     * @throws IllegalArgumentException If the provided transport ID is null.
     */
    public Transport getTransportById(Integer transportId) {
        if (transportId == null) {
            throw new IllegalArgumentException("Transport ID cannot be null for retrieval");
        }

        // Retrieve the transport from the database by ID
        return transportRepository.findById(transportId).orElse(null);
    }

}
