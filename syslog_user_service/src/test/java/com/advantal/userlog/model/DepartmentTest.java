package com.advantal.userlog.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

class DepartmentTest {

    @Test
    void departmentModelGetterSetterTest() {
        // Create a sample department
        String departmentId = "123";
        String departmentName = "IT Department";
        Date createdDate = new Date();
        Date updatedDate = new Date();

        // Create a Department object
        Department department = new Department(departmentId,departmentName,createdDate,updatedDate);

        // Verify the values using getters
        assertEquals(departmentId, department.getDepartmentId());
        assertEquals(departmentName, department.getDepartmentName());
        assertEquals(createdDate, department.getCreatedDate());
        assertEquals(updatedDate, department.getUpdatedDate());
    }
}
