package com.advantal.userlog.controller;


import com.advantal.userlog.CustomException.ResourceNotFoundException;
import com.advantal.userlog.dto.RouterDto;
import com.advantal.userlog.model.Router;
import com.advantal.userlog.serviceImpl.RouterServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

class RouterControllerTest {

    @Mock
    private RouterServiceImpl routerService;

    @InjectMocks
    private RouterController routerController;

    @BeforeEach
    void setup() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this); // Initialize the mocks

    }

    @Test
    @DisplayName("Get All Routers")
    void getAllRouters() {
        // Arrange
        Pageable pageable = mock(Pageable.class);
        Page<Router> routerPage = createMockRouterPage();
        when(routerService.getAllRouters(pageable, "", "", "")).thenReturn(routerPage);

        // Act
        ResponseEntity<Page<Router>> responseEntity = routerController.getAllRouterModels("", "", "", pageable);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(routerPage, responseEntity.getBody());
        verify(routerService, times(1)).getAllRouters(pageable, "", "", "");
    }

//    @Test
//    @DisplayName("Get Router By ID")
//    void getRouterById() throws ResourceNotFoundException {
//        // Arrange
//        String routerId = "123";
//        Router router = createMockRouter();
//        when(routerService.getRouterById(routerId)).thenReturn(ResponseEntity.ok(router));
//
//        // Act
//        ResponseEntity<Router> responseEntity = routerController.getRouterById(routerId);
//
//        // Assert
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertEquals(router, responseEntity.getBody());
//        verify(routerService, times(1)).getRouterById(routerId);
//    }

    @Test
    @DisplayName("Get Router By Invalid ID")

    void getRouterByNonExistingId() throws ResourceNotFoundException {
        // Arrange
        String routerId = "123";
        when(routerService.getRouterById(routerId)).thenThrow(new ResourceNotFoundException("Router not found"));

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            routerController.getRouterById(routerId);
        });
        assertEquals("Router not found", exception.getMessage());
        verify(routerService, times(1)).getRouterById(routerId);
    }

    // Additional test cases for other controller methods can be added here

    private Page<Router> createMockRouterPage() {
        List<Router> routerList = new ArrayList<>();
        routerList.add(createMockRouter());
        routerList.add(createMockRouter());
        return new PageImpl<>(routerList);
    }

    private Router createMockRouter() {
        Router router = new Router();
        // Set mock properties
        return router;
    }
}
