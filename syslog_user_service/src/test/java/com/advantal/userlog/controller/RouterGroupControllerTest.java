package com.advantal.userlog.controller;


import com.advantal.userlog.CustomException.ResourceNotFoundException;
import com.advantal.userlog.dto.RouterGroupDto;
import com.advantal.userlog.model.RouterGroup;
import com.advantal.userlog.serviceImpl.RouterGroupServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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


class RouterGroupControllerTest {

    @Mock
    private RouterGroupServiceImpl routerGroupService;

    @InjectMocks
    private RouterGroupController routerGroupController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this); 
    }

    @Test
    @DisplayName("Get All Router Groups")
    void getAllRouterGroups() {
        // Arrange
        Pageable pageable = mock(Pageable.class);
        Page<RouterGroup> routerGroupPage = createMockRouterGroupPage();
        when(routerGroupService.getAllRouterGroups(pageable, "", "", "")).thenReturn(routerGroupPage);

        // Act
        ResponseEntity<Page<RouterGroup>> responseEntity = routerGroupController.getAllRouterGroups("", "", "", pageable);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(routerGroupPage, responseEntity.getBody());
        verify(routerGroupService, times(1)).getAllRouterGroups(pageable, "", "", "");
    }

//    @Test
//    @DisplayName("Get Router Group By ID")
//
//    void getRouterGroupById() throws ResourceNotFoundException {
//        // Arrange
//        String routerGroupId = "123";
//        RouterGroup routerGroup = createMockRouterGroup();
//        when(routerGroupService.getRouterGroupById(routerGroupId)).thenReturn(ResponseEntity.ok(routerGroup));
//
//        // Act
//        ResponseEntity<RouterGroup> responseEntity = routerGroupController.getRouterGroupById(routerGroupId);
//
//        // Assert
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertEquals(routerGroup, responseEntity.getBody());
//        verify(routerGroupService, times(1)).getRouterGroupById(routerGroupId);
//    }

    @Test
    @DisplayName("Get Router Group By Invalid ID")

    void getRouterGroupByNonExistingId() throws ResourceNotFoundException {
        // Arrange
        String routerGroupId = "123";
        when(routerGroupService.getRouterGroupById(routerGroupId)).thenThrow(new ResourceNotFoundException("Router Group not found"));

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            routerGroupController.getRouterGroupById(routerGroupId);
        });
        assertEquals("Router Group not found", exception.getMessage());
        verify(routerGroupService, times(1)).getRouterGroupById(routerGroupId);
    }

    // Additional test cases for other controller methods can be added here

    private Page<RouterGroup> createMockRouterGroupPage() {
        List<RouterGroup> routerGroupList = new ArrayList<>();
        routerGroupList.add(createMockRouterGroup());
        routerGroupList.add(createMockRouterGroup());
        return new PageImpl<>(routerGroupList);
    }

    private RouterGroup createMockRouterGroup() {
        RouterGroup routerGroup = new RouterGroup();
        // Set mock properties
        return routerGroup;
    }
}
