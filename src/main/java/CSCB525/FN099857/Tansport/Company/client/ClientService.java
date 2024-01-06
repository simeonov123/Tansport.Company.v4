package CSCB525.FN099857.Tansport.Company.client;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing clients in the transport company.
 */
@Service
public class ClientService {

    private final ClientRepository clientRepository;

    /**
     * Constructs a new instance of the ClientService.
     *
     * @param clientRepository The repository for client data.
     */
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    /**
     * Creates a new client with the provided information.
     *
     * @param client The client object to be created. Should be annotated with validation constraints.
     * @return The created client.
     */
    public Client createClient(@Valid Client client) {
        return clientRepository.save(client);
    }

    /**
     * Updates an existing client with the provided information.
     *
     * @param client The client object to be updated. Should be annotated with validation constraints.
     * @return The updated client.
     */
    public Client updateClient(@Valid Client client) {
        // You can also perform additional checks before updating if needed
        return clientRepository.save(client);
    }

    /**
     * Deletes a client based on the provided client object.
     *
     * @param client The client to be deleted.
     */
    public void deleteClient(Client client) {
        clientRepository.deleteById(client.getId());
    }

    /**
     * Retrieves a client based on the provided name.
     *
     * @param name The name of the client to retrieve.
     * @return The client with the specified name, or null if not found.
     */
    public Client getClientByName(@NotBlank String name) {
        return clientRepository.findByName(name);
    }

    /**
     * Retrieves a list of all clients in the transport company.
     *
     * @return A list containing all clients.
     */
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }
}
