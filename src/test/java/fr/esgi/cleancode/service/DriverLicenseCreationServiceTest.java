package fr.esgi.cleancode.service;

import fr.esgi.cleancode.exception.InvalidDriverSocialSecurityNumberException;
import fr.esgi.cleancode.model.DrivingLicence;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;


public class DriverLicenseCreationServiceTest {
    @InjectMocks
    private DriverLicenseCreationService service = new DriverLicenseCreationService();

    @Test
    @DisplayName("Should not create with bad security number length")
    void should_not_create_with_bad_security_number_length() {

        Assertions.assertThrows(
            InvalidDriverSocialSecurityNumberException.class,
            () -> service.createNewDriveLicense("123456789")
        );
    }

    @Test
    @DisplayName("Should not create with bad security number format")
    void should_not_create_with_non_number_characters_in_security_number() {

        Assertions.assertThrows(
                InvalidDriverSocialSecurityNumberException.class,
                () -> service.createNewDriveLicense("12345678901234A")
        );
    }

    @Test
    @DisplayName("Should not create with null number format")
    void should_not_create_with_null_security_number() {

        Assertions.assertThrows(
                InvalidDriverSocialSecurityNumberException.class,
                () -> service.createNewDriveLicense(null)
        );
    }

    @Test
    @DisplayName("Should create with security number")
    void should_create_with_security_number() {

        Assertions.assertDoesNotThrow(
                () -> service.createNewDriveLicense("123456789012345")
        );
    }

    @Test
    @DisplayName("Should create with 12 default points")
    void should_create_with_12_points() {

        DrivingLicence drivingLicence = service.createNewDriveLicense("123456789012345");
        Assertions.assertEquals(12, drivingLicence.getAvailablePoints());
    }
}
