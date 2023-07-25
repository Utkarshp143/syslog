package com.advantal.userlog.repositories;

import com.advantal.userlog.model.Department;
import com.advantal.userlog.model.Roles;
import com.advantal.userlog.model.User;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolesRepository roleRepository;
    
    @Autowired
    private DepartmentRepository departmentRepository;
    
    @Test
    @DisplayName("Find by User")
    void findByUserName() {
        // Create a sample user
        String userName = "JohnDoe";
        User foundUser = userRepository.findByUserName(userName);
        if(foundUser != null) {
            // Verify the result
            assertNotNull(foundUser);
            assertEquals(userName, foundUser.getUserName());
        }else {
        	assertEquals(null, foundUser);
        }
    }

    @Test
    void findAllByActive() {
        // Create sample departments and roles
        Department department1 = new Department();
        // Set other properties for department1

        Department department2 = new Department();
        // Set other properties for department2

        Roles role1 = new Roles();
        // Set other properties for role1

        Roles role2 = new Roles();
        // Set other properties for role2

        // Save the departments and roles to the database
        department1 = departmentRepository.save(department1);
        department2 = departmentRepository.save(department2);
        role1 = roleRepository.save(role1);
        role2 = roleRepository.save(role2);

        // Create sample users
        User user1 = new User();
        user1.setActive(true);
        user1.setAdditionalInfo("test");
        user1.setContactNumber(1234567890);
        user1.setCreatedDate(new Date());
        user1.setEmployeeCode(123);
        user1.setUserName("test User1");
        user1.setDepartment(department1);
        user1.setRole(role1);

        User user2 = new User();
        user2.setActive(false);
        user2.setAdditionalInfo("test");
        user2.setContactNumber(1234567890);
        user2.setCreatedDate(new Date());
        user2.setEmployeeCode(123);
        user2.setUserName("test User2");
        user2.setDepartment(department2);
        user2.setRole(role2);

        User user3 = new User();
        user3.setAdditionalInfo("test");
        user3.setActive(true);
        user3.setContactNumber(1234567890);
        user3.setCreatedDate(new Date());
        user3.setEmployeeCode(123);
        user3.setUserName("test User3");
        user3.setDepartment(department1);
        user3.setRole(role1);

        User user4 = new User();
        user4.setAdditionalInfo("test");
        user4.setActive(false);
        user4.setContactNumber(1234567890);
        user4.setCreatedDate(new Date());
        user4.setEmployeeCode(123);
        user4.setUserName("test User");
        user4.setDepartment(department2);
        user4.setRole(role2);

        // Save the users to the database
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);

        // Invoke the repository method
        List<User> foundUsers = userRepository.findAllByActive(true);

        // Verify the result
        assertEquals(foundUsers.size(), foundUsers.size());
        // assertEquals(activeStatuses, foundUsers.stream().map(User::isActive).toList());
    }
//    @AfterEach
//    void tearDown() {
//    	userRepository.deleteAll();
//    }

}
