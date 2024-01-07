package CSCB525.FN099857.Tansport.Company.transport;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Transport entities. Provides CRUD operations and other data access methods.
 */
@Repository
public interface TransportRepository extends JpaRepository<Transport, Integer> {
}
