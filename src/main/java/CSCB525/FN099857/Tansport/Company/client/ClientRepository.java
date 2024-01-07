package CSCB525.FN099857.Tansport.Company.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing client entities in the transport company.
 * Extends JpaRepository to provide CRUD operations for the Client entity.
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

    /**
     * Retrieves a client based on the provided name.
     *
     * @param name The name of the client to retrieve.
     * @return The client with the specified name, or null if not found.
     */
    Client findByName(String name);
}
