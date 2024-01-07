package CSCB525.FN099857.Tansport.Company;

import CSCB525.FN099857.Tansport.Company.transport.Passengers;
import CSCB525.FN099857.Tansport.Company.transport.TransportService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class TransportTest {
    @Autowired
    private TransportService transportService;

    @Transactional
    @Test
    void testCreatingTransport() {
        Passengers passengers = new Passengers();
        passengers.setNumberOfPassengers(12);

        passengers = (Passengers) transportService.createTransport(passengers);

        assertEquals(12, passengers.getNumberOfPassengers());
    }


    @Transactional
    @Test
    void testUpdatingTransport() {
        Passengers passengers = new Passengers();
        passengers.setNumberOfPassengers(12);

        passengers = (Passengers) transportService.createTransport(passengers);

        passengers.setNumberOfPassengers(6);

        passengers = (Passengers) transportService.updateTransport(passengers);

        assertEquals(6, passengers.getNumberOfPassengers());
    }

    @Transactional
    @Test
    void testDeleteTransport() {
        Passengers passengers = new Passengers();
        passengers.setNumberOfPassengers(12);

        passengers = (Passengers) transportService.createTransport(passengers);

        transportService.deleteTransportById(passengers.getId());

        assertNull(transportService.getTransportById(passengers.getId()));
    }


    @Transactional
    @Test
    void testGetTransportById() {
        Passengers passengers = new Passengers();
        passengers.setNumberOfPassengers(12);

        passengers = (Passengers) transportService.createTransport(passengers);

        passengers = (Passengers) transportService.getTransportById(passengers.getId());
        assertEquals(12, passengers.getNumberOfPassengers());
    }
}
