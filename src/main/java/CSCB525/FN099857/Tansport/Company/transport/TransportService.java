package CSCB525.FN099857.Tansport.Company.transport;

import jakarta.validation.Valid;
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
     * @param commodityTransport The Commodity transport entity to be created and saved.
     * @return The created transport entity.
     */
    public Transport createTransport(@Valid Commodity commodityTransport) {
        return transportRepository.save(commodityTransport);
    }
}
