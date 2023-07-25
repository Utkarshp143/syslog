package com.advantal.userlog.controller;


import com.advantal.userlog.CustomException.ResourceNotFoundException;
import com.advantal.userlog.dto.RolesDto;
import com.advantal.userlog.model.Roles;
import com.advantal.userlog.serviceImpl.RolesServiceImpl;
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

class RolesControllerTest {

    @Mock
    private RolesServiceImpl rolesService;

    @InjectMocks
    private RolesController rolesController;

    @BeforeEach
    void setup() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this); // Initialize the mocks

    }

    @Test
    @DisplayName("Get All Roles")

    void getAllRoles() {
        // Arrange
        Pageable pageable = mock(Pageable.class);
        Page<Roles> rolesPage = createMockRolesPage();
        when(rolesService.getAllRoles(pageable, "", "", "")).thenReturn(rolesPage);

        // Act
        ResponseEntity<Page<Roles>> responseEntity = rolesController.getAllRoleModel("", "", "", pageable);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(rolesPage, responseEntity.getBody());
        verify(rolesService, times(1)).getAllRoles(pageable, "", "", "");
    }

//    @Test
//    @DisplayName("Get Role By ID")
//
//    void getRoleById() throws ResourceNotFoundException {
//        // Arrange
//        String roleId = "123";
//        Roles role = createMockRole();
//        when(rolesService.getRoleById(roleId)).thenReturn(ResponseEntity.ok(role));
//
//        // Act
//        ResponseEntity<Roles> responseEntity = rolesController.getRoleById(roleId);
//
//        // Assert
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertEquals(role, responseEntity.getBody());
//        verify(rolesService, times(1)).getRoleById(roleId);
//    }

    @Test
    @DisplayName("Get Role By Invalid ID")

    void getRoleByNonExistingId() throws ResourceNotFoundException {
        // Arrange
        String roleId = "123";
        when(rolesService.getRoleById(roleId)).thenThrow(new ResourceNotFoundException("Role not found"));

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            rolesController.getRoleById(roleId);
        });
        assertEquals("Role not found", exception.getMessage());
        verify(rolesService, times(1)).getRoleById(roleId);
    }

    // Additional test cases for other controller methods can be added here

    private Page<Roles> createMockRolesPage() {
        List<Roles> roleList = new ArrayList<>();
        roleList.add(createMockRole());
        roleList.add(createMockRole());
        return new PageImpl<>(roleList);
    }

    private Roles createMockRole() {
        Roles role = new Roles();
        // Set mock properties
        return role;
    }
}
