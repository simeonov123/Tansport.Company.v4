package CSCB525.FN099857.Tansport.Company;

import CSCB525.FN099857.Tansport.Company.copany.CompanyService;
import CSCB525.FN099857.Tansport.Company.employee.DriversLicense;
import CSCB525.FN099857.Tansport.Company.mtv.MotorisedTransportVehicle;
import CSCB525.FN099857.Tansport.Company.mtv.MotorisedTransportVehicleService;
import CSCB525.FN099857.Tansport.Company.mtv.VehicleType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class MotorisedTransportVehicleTest {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private MotorisedTransportVehicleService mtvService;


    @Transactional
    @Test
    void createVehicle() {
        String expectedModel = "Mercedes Benz C class V203";
        MotorisedTransportVehicle vehicle = new MotorisedTransportVehicle();
        vehicle.setVehicleType(VehicleType.CAR);
        vehicle.setModel(expectedModel);
        vehicle.setRequiredDriversLicenseCategory(DriversLicense.B);

        vehicle = mtvService.createVehicle(vehicle);

        assertEquals(expectedModel, vehicle.getModel());
    }

    @Transactional
    @Test
    void testDeleteVehicle() {
        String expectedModel = "Mercedes Benz C class V203";
        MotorisedTransportVehicle vehicle = new MotorisedTransportVehicle();
        vehicle.setVehicleType(VehicleType.CAR);
        vehicle.setModel(expectedModel);
        vehicle.setRequiredDriversLicenseCategory(DriversLicense.B);

        vehicle = mtvService.createVehicle(vehicle);


        mtvService.deleteVehicleById(vehicle.getId());

        vehicle = mtvService.getVehicleByModel(expectedModel);

        assertNull(vehicle);
    }


    @Transactional
    @Test
    void testGetVehiclesByModel() {
        String expectedModel = "Mercedes Benz C class V20";

        for (int i = 0; i < 5; i++) {
            MotorisedTransportVehicle vehicle = new MotorisedTransportVehicle();
            vehicle.setVehicleType(VehicleType.CAR);
            vehicle.setModel(expectedModel + i);
            vehicle.setRequiredDriversLicenseCategory(DriversLicense.B);

            mtvService.createVehicle(vehicle);
        }


        MotorisedTransportVehicle vehicleByName = mtvService.getVehicleByModel(expectedModel + 0);

        assertEquals(expectedModel + 0, vehicleByName.getModel());
    }

    @Transactional
    @Test
    void testUpdateVehicle() {
        String model = "Mercedes Benz C class V203";
        MotorisedTransportVehicle vehicle = new MotorisedTransportVehicle();
        vehicle.setVehicleType(VehicleType.CAR);
        vehicle.setModel(model);
        vehicle.setRequiredDriversLicenseCategory(DriversLicense.B);

        vehicle = mtvService.createVehicle(vehicle);

        String expectedModel = "BMW blah blah";
        vehicle.setModel(expectedModel);

        mtvService.updateVehicle(vehicle);

        assertEquals(expectedModel, vehicle.getModel());
    }
}
