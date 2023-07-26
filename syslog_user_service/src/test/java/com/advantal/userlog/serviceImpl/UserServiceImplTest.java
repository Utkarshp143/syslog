package com.advantal.userlog.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.advantal.userlog.model.User;
import com.advantal.userlog.repositories.UserRepository;
import com.advantal.userlog.dto.Response;
import com.advantal.userlog.model.Department;
import com.advantal.userlog.model.Roles;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;


@DisplayName("UserServiceTest")
@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private MongoTemplate mongoTemplateMock;

    @InjectMocks
    private UserServiceImpl userService;
    
    private User user;
    
    private User userWOutRole;
    
    private User blankUser;
    
    private User duplicateUser;
    
    
    @BeforeEach
    void setUp() {
        // Create a Department instance
        Department department = Department.builder()
                .departmentId("dept1")
                .departmentName("Sample Department")
                .createdDate(new Date())
                .updatedDate(new Date())
                .build();

        // Create a Roles instance
        Roles role = Roles.builder()
                .roleId("role1")
                .roleName("Sample Role")
                .createdDate(new Date())
                .updatedDate(new Date())
                .active(true)
                .build();

        // Create a User instance
        user = User.builder()
                .userId("user1")
                .userName("Sample User")
                .password("password123")
                .contactNumber(1234567890)
                .employeeCode(1001)
                .department(department) 
                .additionalInfo("Additional information about the user")
                .createdDate(new Date())
                .role(role) 
                .active(true)
                .build();
        
        userWOutRole = User.builder()
                .userId("user2")
                .userName("Sample User")
                .password("password123")
                .contactNumber(1234567890)
                .employeeCode(1001)
                .department(department) 
                .additionalInfo("Additional information about the user")
                .createdDate(new Date())
                .active(true)
                .build();
        
        duplicateUser = User.builder()
                .userId("user3")
                .userName("Sample User")
                .password("password123")
                .contactNumber(1234567890)
                .employeeCode(1001)
                .department(department) 
                .additionalInfo("Additional information about the user")
                .createdDate(new Date())
                .role(role) 
                .active(true)
                .build();
        
        blankUser = User.builder()
                .userId("user1")
                .userName("")
                .password("password123")
                .contactNumber(1234567890)
                .employeeCode(1001)
                .department(department) 
                .additionalInfo("Additional information about the user")
                .createdDate(new Date())
                .role(role) 
                .active(true)
                .build();
    }

 // JUnit test for saveUser method
    @Test
    @DisplayName("Test saving a new user")
    public void saveNewUser() {
        Response response = userService.createUser(user);
        assertEquals("201", response.getStatusCode());
        assertEquals("User Created Successfully!", response.getMessage());
    }

    @Test
    @DisplayName("Test saving a user without a role")
    public void saveUserWithoutRole() {
        Response response = userService.createUser(userWOutRole);
        assertEquals("409", response.getStatusCode());
        assertEquals("Can't create User as no Role is selected!", response.getMessage());
    }

    @Test
    @DisplayName("Test saving a user with a blank username")
    public void saveUserWithBlankUsername() {
        Response response = userService.createUser(blankUser);
        assertEquals("400", response.getStatusCode());
        assertEquals("Username cannot be blank!", response.getMessage());
    }

    @Test
    @DisplayName("Test saving a duplicate user")
    public void saveDuplicateUser() {
        Response response = userService.createUser(duplicateUser);
        assertEquals("409", response.getStatusCode());
        assertEquals("User already Exists!", response.getMessage());
    }

}    
    
//    @Test
//    @DisplayName("Create Valid User")
//    public void createValidUser() {
//        User validUser = new User();
//        validUser.setUserId("123");
//        validUser.setUserName("Test User");
//        validUser.setPassword("password123");
//        validUser.setContactNumber(1234567890);
//        validUser.setEmployeeCode(12345);
//        validUser.setDepartment(new Department());
//        validUser.setAdditionalInfo("Additional info");
//        validUser.setCreatedDate(new Date());
//        validUser.setRole(new Roles());
//        validUser.setActive(true);
//
//        when(userRepositoryMock.findByUserName(any(String.class))).thenReturn(null);
//
//        when(userRepositoryMock.save(any(User.class))).thenReturn(validUser);
//
//        Response response = userService.createUser(validUser);
//
//        assertEquals("201", response.getStatusCode());
//        assertEquals("User Created Successfully!", response.getMessage());
//
//        verify(userRepositoryMock, times(1)).findByUserName("test user");
//
//        verify(userRepositoryMock, times(1)).save(validUser);
//    }
//
//    @Test
//    @DisplayName("Create Existing User")
//    public void createExistingUser() {
//        User existingUser = new User();
//        existingUser.setUserId("456");
//        existingUser.setUserName("Existing User");
//        existingUser.setPassword("existingPassword");
//
//        when(userRepositoryMock.findByUserName(any(String.class))).thenReturn(existingUser);
//
//        User newUser = new User();
//        newUser.setUserId("789");
//        newUser.setUserName("Existing User"); 
//        newUser.setPassword("newPassword");
//
//        Response response = userService.createUser(newUser);
//
//        assertEquals("409", response.getStatusCode());
//        assertEquals("User already exists!", response.getMessage());
//
//        verify(userRepositoryMock, times(1)).findByUserName("existing user");
//
//        verify(userRepositoryMock, never()).save(any(User.class));
//    }
//    @Test
//    @DisplayName("Create User with Blank Username")
//    public void createUserWithBlankUsername() {
//        User userWithBlankUsername = new User();
//        userWithBlankUsername.setUserId("999");
//        userWithBlankUsername.setUserName(""); // Setting a blank username
//        userWithBlankUsername.setPassword("blankPassword");
//        //unnecessary stubbing
//      //  when(userRepositoryMock.findByUserName(any(String.class))).thenReturn(null);
//
//        Response response = userService.createUser(userWithBlankUsername);
//
//        assertEquals("400", response.getStatusCode());
//        assertEquals("Username cannot be blank!", response.getMessage());
//
//    }
//    @Test
//    void getAllUser() {
//        // User 1
//        User user1 = new User();
//        user1.setUserId("1");
//        user1.setUserName("User1");
//        user1.setPassword("password123");
//        user1.setContactNumber(1234567890);
//        user1.setEmployeeCode(1001);
//        user1.setDepartment(new Department());
//        user1.setAdditionalInfo("Additional information about User 1");
//        user1.setCreatedDate(new Date());
//        user1.setRole(new Roles());
//        user1.setActive(true);
//
//        // User 2
//        User user2 = new User();
//        user2.setUserId("2");
//        user2.setUserName("User2");
//        user2.setPassword("securePassword");
//        user2.setContactNumber(9876543210L);
//        user2.setEmployeeCode(1002);
//        user2.setDepartment(new Department());
//        user2.setAdditionalInfo("Additional information about User 2");
//        user2.setCreatedDate(new Date());
//        user2.setRole(new Roles());
//        user2.setActive(true);
//
//        List<User> sampleUsers = new ArrayList<>();
//        sampleUsers.add(user1);
//        sampleUsers.add(user2);
//
//        Page<User> page = new PageImpl<>(sampleUsers);
//
//       // when(userRepositoryMock.findAll(any(Pageable.class))).thenReturn(page);
//
//        int pageNumber = 0;
//        int size = 10;
//        Pageable pageable = PageRequest.of(pageNumber, size);
//        Page<User> resultPage = userService.getAllUsers(pageable, "", "", "");
//
//        List<User> resultUsers = resultPage.getContent();
//
//        // Verify 
//        assertEquals(0, resultUsers.size());
//    }

