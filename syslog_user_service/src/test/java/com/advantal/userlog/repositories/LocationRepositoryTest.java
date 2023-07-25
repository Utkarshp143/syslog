package com.advantal.userlog.repositories;

import com.advantal.userlog.model.Location;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;

@DataMongoTest
class LocationRepositoryTest {

    @Autowired
    private LocationRepository locationRepository;

    @Test
    @DisplayName("Find by Location")
    void findByLocationName() {
        // Create a sample location
        String locationName = "New York";
        
        // Invoke the repository method
        Location foundLocation = locationRepository.findByLocationName(locationName);
        if(foundLocation != null) {
            // Verify the result
            assertNotNull(foundLocation);
            assertEquals(locationName, foundLocation.getLocationName());
        }else {
        	assertEquals(null, foundLocation);
        }
    }
}
