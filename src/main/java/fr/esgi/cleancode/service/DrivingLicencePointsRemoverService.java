package fr.esgi.cleancode.service;

import fr.esgi.cleancode.database.InMemoryDatabase;
import fr.esgi.cleancode.exception.ResourceNotFoundException;
import fr.esgi.cleancode.model.DrivingLicence;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class DrivingLicencePointsRemoverService {

    private final InMemoryDatabase database;

    public DrivingLicence removePointsFromDrivingLicense(UUID drivingLicenceId, Integer points) throws ResourceNotFoundException, IllegalArgumentException {
        if (!this.isValidPoints(points)) {
            throw new IllegalArgumentException("The points must be between 1 and 12");
        }

        DrivingLicence drivingLicence = database.findById(drivingLicenceId)
                .orElseThrow(() -> new ResourceNotFoundException("The driving license with id " + drivingLicenceId + " does not exist"));

        DrivingLicence adjustedDrivingLicence = drivingLicence.withAvailablePoints(this.subtractPoints(drivingLicence.getAvailablePoints(), points));

        return database.saveDrivingLicence(drivingLicenceId, adjustedDrivingLicence);
    }

    private Integer subtractPoints(Integer basePoints, Integer pointsToRemove) {
        return Math.max(basePoints - pointsToRemove, 0);
    }

    private boolean isValidPoints(Integer points) {
        return points != null && points > 0 && points <= 12;
    }
}
