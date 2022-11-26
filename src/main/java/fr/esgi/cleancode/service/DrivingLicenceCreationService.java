package fr.esgi.cleancode.service;

import fr.esgi.cleancode.database.InMemoryDatabase;
import fr.esgi.cleancode.exception.InvalidDriverSocialSecurityNumberException;
import fr.esgi.cleancode.model.DrivingLicence;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class DrivingLicenceCreationService {

    private final InMemoryDatabase database;

    public DrivingLicence createNewDrivingLicence(String socialSecurityNumber) throws InvalidDriverSocialSecurityNumberException {
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
