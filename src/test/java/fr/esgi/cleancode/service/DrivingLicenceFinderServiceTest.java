package fr.esgi.cleancode.service;

import fr.esgi.cleancode.database.InMemoryDatabase;
import fr.esgi.cleancode.model.DrivingLicence;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.UUID;

class DrivingLicenceFinderServiceTest {
    @Mock
    private InMemoryDatabase database = InMemoryDatabase.getInstance();

    @InjectMocks
    private DrivingLicenceFinderService service = new DrivingLicenceFinderService(database);

    DrivingLicence fakeDrivingLicence;

    @BeforeEach
    void setUpDatabase() {
        DrivingLicenseCreationService creationService = new DrivingLicenseCreationService(database);

        this.fakeDrivingLicence = creationService.createNewDriveLicense("123456789012345");
    }

    @Test
    @DisplayName("Should find a driving licence by id")
    void should_find() {
        var foundDrivingLicense = service.findById(fakeDrivingLicence.getId());

        Assertions.assertTrue(foundDrivingLicense.isPresent());
    }

    @Test
    @DisplayName("Should not find a driving licence by id")
    void should_not_find() {
        var foundDrivingLicense = service.findById(UUID.randomUUID());

        Assertions.assertTrue(foundDrivingLicense.isEmpty());
    }
}