package com.advantal.userlog.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LocationTest {

    @Test
    void locationModelGetterSetterTest() {
        // Create a sample location
        String locationID = "123";
        String locationName = "New York";

        // Create a Location object
        Location location = new Location(locationID,locationName);

        // Verify the values using getters
        assertEquals(locationID, location.getLocationID());
        assertEquals(locationName, location.getLocationName());
    }
}
