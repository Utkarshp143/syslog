package com.advantal.userlog.repositories;


import com.advantal.userlog.model.Department;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.springframework.test.context.ActiveProfiles;


@DataMongoTest
@ActiveProfiles("test")
class DepartmentRepositoryTest {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    @DisplayName("Find by Department")
    void findByDepartmentName() {
        // Create a sample department with a unique name
        String departmentName = "IT Department";
        
        // Invoke the repository method
        Department foundDepartment = departmentRepository.findByDepartmentName(departmentName);
        if(foundDepartment != null) {
        	// Verify the result
            assertNotNull(foundDepartment);
            assertEquals(departmentName, foundDepartment.getDepartmentName());
        }
        else {
        	assertEquals(null, foundDepartment);
        }
    }
}
