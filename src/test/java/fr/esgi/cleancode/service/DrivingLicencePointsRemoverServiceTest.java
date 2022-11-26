package fr.esgi.cleancode.service;

import fr.esgi.cleancode.database.InMemoryDatabase;
import fr.esgi.cleancode.exception.InvalidDriverSocialSecurityNumberException;
import fr.esgi.cleancode.model.DrivingLicence;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.UUID;

class DrivingLicencePointsRemoverServiceTest {
    @Mock
    private InMemoryDatabase database = InMemoryDatabase.getInstance();

    @InjectMocks
    private DrivingLicencePointsRemoverService service = new DrivingLicencePointsRemoverService(database);

    DrivingLicence fakeDrivingLicence;

    @BeforeEach
    void setUpDatabase() {
        UUID id = UUID.randomUUID();
        this.fakeDrivingLicence = database.saveDrivingLicence(
            id,
            DrivingLicence.builder()
                .id(id)
                .driverSocialSecurityNumber("123456789012345")
                .availablePoints(6)
                .build()
        );
    }

    @Test
    @DisplayName("Should not remove points from driving licence if input value is null")
    void should_not_subtract_point_if_value_is_null() {

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> service.removePointsFromDrivingLicense(fakeDrivingLicence.getId(), null)
        );
    }

    @Test
    @DisplayName("Should not remove points from driving licence if input value is negative")
    void should_not_subtract_point_if_value_is_negative() {

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> service.removePointsFromDrivingLicense(fakeDrivingLicence.getId(), -10)
        );
    }

    @Test
    @DisplayName("Should not remove points from driving licence if input value is 0")
    void should_not_subtract_point_if_value_is_0() {

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> service.removePointsFromDrivingLicense(fakeDrivingLicence.getId(), 0)
        );
    }

    @Test
    @DisplayName("Should not remove points from driving licence if input value is superior to 12")
    void should_not_subtract_point_if_is_superior_to_12() {

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> service.removePointsFromDrivingLicense(fakeDrivingLicence.getId(), 13)
        );
    }

    @Test
    @DisplayName("Should set points to 0 if input value is superior to current points")
    void should_set_points_to_0_if_input_value_is_superior_to_current_points() {

        DrivingLicence updatedDrivingLicence = service.removePointsFromDrivingLicense(fakeDrivingLicence.getId(), 8);
        Assertions.assertEquals(0, updatedDrivingLicence.getAvailablePoints());
    }

    @Test
    @DisplayName("Should subtract points from driving licence")
    void should_subtract_points_from_driving_licence() {

        DrivingLicence updatedDrivingLicence = service.removePointsFromDrivingLicense(fakeDrivingLicence.getId(), 4);
        Assertions.assertEquals(2, updatedDrivingLicence.getAvailablePoints());
    }
}