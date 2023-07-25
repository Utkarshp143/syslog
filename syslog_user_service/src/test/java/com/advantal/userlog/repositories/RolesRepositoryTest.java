package com.advantal.userlog.repositories;

import com.advantal.userlog.model.Roles;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;

@DataMongoTest
class RolesRepositoryTest {

    @Autowired
    private RolesRepository rolesRepository;

    @Test
    @DisplayName("Find by Role")
    void findByRoleName() {
        // Create a sample role
        String roleName = "Admin";
        Roles foundRole = rolesRepository.findByRoleName(roleName);
        // if role already exists
        if(foundRole != null) {
        	 // Verify the result
            assertNotNull(foundRole);
            assertEquals(roleName, foundRole.getRoleName());
        }
        // if role doesn't exists
        else {
        	assertEquals(null, foundRole);
        }
    }

    
}
