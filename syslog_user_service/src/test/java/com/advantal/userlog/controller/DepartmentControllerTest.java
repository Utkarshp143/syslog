package com.advantal.userlog.controller;

import com.advantal.userlog.CustomException.ResourceNotFoundException;
import com.advantal.userlog.model.Department;
import com.advantal.userlog.serviceImpl.DepartmentServiceImpl;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.advantal.userlog.controller.DepartmentController;
import com.advantal.userlog.model.Department;
import com.advantal.userlog.serviceImpl.DepartmentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class DepartmentControllerTest {

    @Mock
    private DepartmentServiceImpl departmentService;

    @InjectMocks
    private DepartmentController departmentController;

    private Pageable pageable;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this); // Initialize the mocks
        pageable = PageRequest.of(0, 10, Sort.by("field").ascending());
    }

    @Test
    void getAllDepartmentModel() {
        // Mock the necessary objects and behavior
        String searchTerm = "search";
        String order = "asc";
        String field = "fieldName";
        Page<Department> mockDepartmentPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
        when(departmentService.getAllDepartments(any(Pageable.class), eq(searchTerm), eq(order), eq(field)))
                .thenReturn(mockDepartmentPage);

        // Call the controller method
        ResponseEntity<Page<Department>> response = departmentController.getAllDepartmentModel(searchTerm, order, field, pageable);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockDepartmentPage, response.getBody());

        // Verify that the service method was called with the correct arguments
        verify(departmentService).getAllDepartments(any(Pageable.class), eq(searchTerm), eq(order), eq(field));
    }

//    @Test
//    @DisplayName("Get All Departments By ID")
//
//    void getDepartmentById() throws ResourceNotFoundException {
//        // Arrange
//        String departmentId = "123";
//        Department department = createMockDepartment();
//        when(departmentService.getDepartmentById(departmentId)).thenReturn(ResponseEntity.ok(department));
//
//        // Act
//        ResponseEntity<Department> responseEntity = departmentController.getDepartmentById(departmentId);
//
//        // Assert
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertEquals(department, responseEntity.getBody());
//        verify(departmentService, times(1)).getDepartmentById(departmentId);
//    }

    @Test
    @DisplayName("Get All Departments By Non Existing ID")

    void getDepartmentByNonExistingId() throws ResourceNotFoundException {
        // Arrange
        String departmentId = "123";
        when(departmentService.getDepartmentById(departmentId)).thenThrow(new ResourceNotFoundException("Department not found"));

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            departmentController.getDepartmentById(departmentId);
        });
        assertEquals("Department not found", exception.getMessage());
        verify(departmentService, times(1)).getDepartmentById(departmentId);
    }

    // Additional test cases for other controller methods can be added here

    private Department createMockDepartment() {
        Department department = new Department();
        // Set mock properties
        return department;
    }

//    private DepartmentDto createMockDepartmentDto() {
//        DepartmentDto departmentDto = new DepartmentDto();
//        // Set mock properties
//        return departmentDto;
//    }
}
