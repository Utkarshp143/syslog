package com.advantal.userlog.controller;

import com.advantal.userlog.CustomException.ResourceNotFoundException;
import com.advantal.userlog.model.Privilege;
import com.advantal.userlog.dto.PrivilegeDto;
import com.advantal.userlog.dto.Response;
import com.advantal.userlog.serviceImpl.PrivilegeServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PrivilegeControllerTest {

    @Mock
    private PrivilegeServiceImpl privilegeService;

    @InjectMocks
    private PrivilegeController privilegeController;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this); // Initialize the mocks
    }

    @Test
    @DisplayName("Get All Privileges")

    public void getAllPrivileges() {
        // Mock the necessary objects and behavior
        Pageable pageable = PageRequest.of(0, 10);
        Page<Privilege> mockPrivileges = new PageImpl<>(Collections.emptyList(), pageable, 0);
        when(privilegeService.getAllPrivileges(any(Pageable.class), anyString(), anyString(), anyString()))
                .thenReturn(mockPrivileges);

        // Call the controller method
        ResponseEntity<Page<PrivilegeDto>> response = privilegeController.getAllPrivileges("", "", "", pageable);

        // Verify the response
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(mockPrivileges, response.getBody());

        // Verify that the service method was called with the correct arguments
        verify(privilegeService).getAllPrivileges(any(Pageable.class), anyString(), anyString(), anyString());
    }

//    @Test
//    @DisplayName("Get Privilege By ID")
//
//    public void getPrivilegeById() throws ResourceNotFoundException {
//        // Mock the necessary objects and behavior
//        String privilegeId = "1";
//        Privilege mockPrivilege = new Privilege();
//        when(privilegeService.getPrivilegeById(anyString())).thenReturn(ResponseEntity.ok(mockPrivilege));
//
//        // Call the controller method
//        ResponseEntity<Privilege> response = privilegeController.getPrivilegeById(privilegeId);
//
//        // Verify the response
//        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
//        Assert.assertEquals(mockPrivilege, response.getBody());
//
//        // Verify that the service method was called with the correct argument
//        verify(privilegeService).getPrivilegeById(privilegeId);
//    }

    @Test
    @DisplayName("Create Privilege")

    public void createPrivilege() {
        // Mock the necessary objects and behavior
        Privilege mockPrivilege = new Privilege();
        mockPrivilege.setPrivilegeName("testPrivilege");
        Response res = new Response();
        res.setStatusCode("201");
        res.setMessage("Privilege Created Successfully!");
       
        when(privilegeService.createPrivilege(any(Privilege.class))).thenReturn(res);

        // Call the controller method
        ResponseEntity<Response> response = privilegeController.createPrivilege(mockPrivilege);

        // Verify the response
        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assert.assertEquals("201", response.getBody().getStatusCode());
        Assert.assertEquals("Privilege Created Successfully!", response.getBody().getMessage());

        // Verify that the service method was called with the correct argument
        verify(privilegeService).createPrivilege(any(Privilege.class));
    }

    @Test
    @DisplayName("Update Privilege")

    public void updatePrivilege() throws ResourceNotFoundException {
        // Mock the necessary objects and behavior
        String privilegeId = "1";
        Privilege mockPrivilege = new Privilege();
        when(privilegeService.updatePrivilege(eq(privilegeId), any(Privilege.class))).thenReturn(ResponseEntity.ok(mockPrivilege));

        // Call the controller method
        ResponseEntity<Privilege> response = privilegeController.updatePrivilege(privilegeId, mockPrivilege);

        // Verify the response
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(mockPrivilege, response.getBody());

        // Verify that the service method was called with the correct arguments
        verify(privilegeService).updatePrivilege(eq(privilegeId), any(Privilege.class));
    }

    @Test
    @DisplayName("Delete Privilege")

    public void deletePrivilege() throws ResourceNotFoundException {
        // Mock the necessary objects and behavior
        String privilegeId = "1";

        String response = "Privilege deleted Successfully!";
        when(privilegeService.deletePrivilege(privilegeId)).thenReturn(response);

        // Call the controller method
        String deleteResponse = privilegeController.deletePrivilege(privilegeId);

        // Verify the response
        Assert.assertEquals("Privilege deleted Successfully!", deleteResponse);

        // Verify that the service method was called with the correct argument
        verify(privilegeService).deletePrivilege(privilegeId);
    }

}
