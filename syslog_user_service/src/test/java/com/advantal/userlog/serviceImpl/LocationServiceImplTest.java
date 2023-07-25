package com.advantal.userlog.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.advantal.userlog.dto.LocationDto;
import com.advantal.userlog.dto.Response;
import com.advantal.userlog.model.Location;
import com.advantal.userlog.repositories.LocationRepository;

@ExtendWith(MockitoExtension.class)
public class LocationServiceImplTest {
	@Mock
    private LocationRepository locationRepository;
    
    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private LocationServiceImpl locationService;

    private LocationDto location;

    @BeforeEach
    void setUp() {
    	location = LocationDto.builder()
    			.locationName("Gurgaon")
    			.build();
    }

    // JUnit test for saveLocation method
    @DisplayName("JUnit test for saveLocation method")
    @Test
    public void saveLocation(){
    	if(locationRepository.findByLocationName(location.getLocationName()) == null) {
            Response response = locationService.createLocation(location);
            assertEquals("201",response.getStatusCode());
            assertEquals("Location Created Successfully!", response.getMessage());
    	}else if(location.getLocationName().isEmpty()) {
            Response response = locationService.createLocation(location);
            assertEquals("400",response.getStatusCode());
            assertEquals("Location Name cannot be blank!", response.getMessage());
    	}
    	else {
            Response response = locationService.createLocation(location);
            assertEquals("409",response.getStatusCode());
            assertEquals("Location already Exists!", response.getMessage()); 
    	}
    }
}