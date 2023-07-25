package com.advantal.userlog.controller;

import com.advantal.userlog.CustomException.ResourceNotFoundException;
import com.advantal.userlog.dto.UserDto;
import com.advantal.userlog.model.User;
import com.advantal.userlog.serviceImpl.UserServiceImpl;
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

class UserControllerTest {

    @Mock
    private UserServiceImpl userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Get all Users")
    void getAllUsers() {
        // Arrange
        Pageable pageable = mock(Pageable.class);
        Page<User> userPage = createMockUserPage();
        when(userService.getAllUsers(pageable, "", "", "")).thenReturn(userPage);

        // Act
        ResponseEntity<Page<User>> responseEntity = userController.getAllUserModel("", "", "", pageable);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(userPage, responseEntity.getBody());
        verify(userService, times(1)).getAllUsers(pageable, "", "", "");
    }

//    @Test
//    @DisplayName("Get User By ID")
//    void getUserById() throws ResourceNotFoundException {
//        // Arrange
//        String userId = "123";
//        User user = createMockUser();
//        when(userService.getUserById(userId)).thenReturn(ResponseEntity.ok(user));
//
//        // Act
//        ResponseEntity<User> responseEntity = userController.getUserById(userId);
//
//        // Assert
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertEquals(user, responseEntity.getBody());
//        verify(userService, times(1)).getUserById(userId);
//    }

    @Test
    @DisplayName("Get User By Invalid ID")
    void getUserByNonExistingId() throws ResourceNotFoundException {
        // Arrange
        String userId = "123";
        when(userService.getUserById(userId)).thenThrow(new ResourceNotFoundException("User not found"));

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            userController.getUserById(userId);
        });
        assertEquals("User not found", exception.getMessage());
        verify(userService, times(1)).getUserById(userId);
    }

    // Additional test cases for other controller methods can be added here

    private Page<User> createMockUserPage() {
        List<User> userList = new ArrayList<>();
        userList.add(createMockUser());
        userList.add(createMockUser());
        return new PageImpl<>(userList);
    }

    private User createMockUser() {
        User user = new User();
        // Set mock properties
        return user;
    }
}
