package com.advantal.userlog.controller;

import com.advantal.userlog.CustomException.ResourceNotFoundException;
import com.advantal.userlog.dto.LocationDto;
import com.advantal.userlog.model.Department;
import com.advantal.userlog.model.Location;
import com.advantal.userlog.serviceImpl.LocationServiceImpl;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

class LocationControllerTest {

    @Mock
    private LocationServiceImpl locationService;

    @InjectMocks
    private LocationController locationController;

    private Pageable pageable;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this); // Initialize the mocks
        pageable = PageRequest.of(0, 10, Sort.by("field").ascending());
    }

    @Test
    void getAllLocationModel() {
        // Mock the necessary objects and behavior
        String searchTerm = "search";
        String order = "asc";
        String field = "fieldName";
        Page<Location> mockLocationPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
        when(locationService.getAllLocations(any(Pageable.class), eq(searchTerm), eq(order), eq(field)))
                .thenReturn(mockLocationPage);

        // Call the controller method
        ResponseEntity<Page<Location>> response = locationController.getAllLocationModel(searchTerm, order, field, pageable);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockLocationPage, response.getBody());

        // Verify that the service method was called with the correct arguments
        verify(locationService).getAllLocations(any(Pageable.class), eq(searchTerm), eq(order), eq(field));
    }

//    @Test
//    @DisplayName("Get All Locations By ID")

//    void getLocationById_ExistingId() throws ResourceNotFoundException {
//        // Arrange
//        String locationId = "123";
//        Location location = createMockLocation();
//        when(locationService.getLocationById(locationId)).thenReturn(ResponseEntity.ok(location));
//
//        // Act
//        ResponseEntity<Location> responseEntity = locationController.getLocationById(locationId);
//
//        // Assert
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertEquals(location, responseEntity.getBody());
//        verify(locationService, times(1)).getLocationById(locationId);
//    }

    @Test
    @DisplayName("Get All Locations By Invalid ID")
    
    void getLocationByNonExistingId() throws ResourceNotFoundException {
        // Arrange
        String locationId = "123";
        when(locationService.getLocationById(locationId)).thenThrow(new ResourceNotFoundException("Location not found"));

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            locationController.getLocationById(locationId);
        });
        assertEquals("Location not found", exception.getMessage());
        verify(locationService, times(1)).getLocationById(locationId);
    }

    // Additional test cases for other controller methods can be added here

    private Page<Location> createMockLocationPage() {
        List<Location> locationList = new ArrayList<>();
        locationList.add(createMockLocation());
        locationList.add(createMockLocation());
        return new PageImpl<>(locationList);
    }

    private Location createMockLocation() {
        Location location = new Location();
        // Set mock properties
        return location;
    }
}
