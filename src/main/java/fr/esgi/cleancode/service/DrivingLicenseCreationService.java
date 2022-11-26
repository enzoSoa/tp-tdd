package fr.esgi.cleancode.service;

/*
BUSINESS RULES
1. Checking the validity of a social security number
1. Must not be zero
2. Must only contain numbers
3. Must be 15 in length
4. If the social security number is not valid throw an exception of type
InvalidDriverSocialSecurityNumberException
2. Creation of the driving license
1. The driver's license identifier must be generated thanks to the class
DrivingLicenceIdGenerationService
2. When a driver's license is created, it has a total of 12 points
3. The validated social security number must be entered when creating a driving license
4. Save the new driver's license
5. Return the new driver's license
 */

import fr.esgi.cleancode.database.InMemoryDatabase;
import fr.esgi.cleancode.exception.InvalidDriverSocialSecurityNumberException;
import fr.esgi.cleancode.model.DrivingLicence;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class DrivingLicenseCreationService {

    private final InMemoryDatabase database;

    public DrivingLicence createNewDriveLicense(String socialSecurityNumber) throws InvalidDriverSocialSecurityNumberException {
        if (!this.isValidSocialSecurityNumber(socialSecurityNumber)) {
            throw new InvalidDriverSocialSecurityNumberException("The social security number format is not valid");
        }

        UUID drivingLicenceId = new DrivingLicenceIdGenerationService().generateNewDrivingLicenceId();

        DrivingLicence drivingLicence = DrivingLicence.builder()
                .id(drivingLicenceId)
                .driverSocialSecurityNumber(socialSecurityNumber)
                .build();

        return database.saveDrivingLicence(drivingLicenceId, drivingLicence);
    }

    private boolean isValidSocialSecurityNumber(String socialSecurityNumber)  {
        if (socialSecurityNumber == null) {
            return false;
        }

        return socialSecurityNumber.matches("^[0-9]{15}$");
    }
}
