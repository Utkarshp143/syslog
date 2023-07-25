package com.advantal.userlog.repositories;


import com.advantal.userlog.model.Privilege;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;

@DataMongoTest
class PrivilegeRepositoryTest {

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Test
    @DisplayName("Find by Privilege")
    void findByPrivilegeName() {
        // Create a sample privilege
        String privilegeName = "ADMIN";
        Privilege foundPrivilege = privilegeRepository.findByPrivilegeName(privilegeName);
        if(foundPrivilege != null) {
            assertNotNull(foundPrivilege);
            assertEquals(privilegeName, foundPrivilege.getPrivilegeName());
        }else {
            assertEquals(null, foundPrivilege);
        }
    }
}
