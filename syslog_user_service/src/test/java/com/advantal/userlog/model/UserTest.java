package com.advantal.userlog.model;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

@DisplayName("UserTest")
public class UserTest {

	@Test
	@DisplayName("Test the Values")
	public void testUser() {
		
        // Create a User object
        
		User user = new User();
        user.setUserId("123");
        user.setUserName("Test User");
        user.setPassword("password123");
        user.setContactNumber(1234567890);
        user.setEmployeeCode(12345);
        user.setDepartment(new Department());
        user.setAdditionalInfo("Additional info");
        user.setCreatedDate(new Date());
        user.setRole(new Roles());
        user.setActive(true);

        // Verify the values are set correctly
        Assert.assertEquals("123", user.getUserId());
        Assert.assertEquals("Test User", user.getUserName());
        Assert.assertEquals("password123", user.getPassword());
        Assert.assertEquals(1234567890, user.getContactNumber());
        Assert.assertEquals(12345, user.getEmployeeCode(), 0);
        Assert.assertNotNull(user.getDepartment());
        Assert.assertEquals("Additional info", user.getAdditionalInfo());
        Assert.assertNotNull(user.getCreatedDate());
        Assert.assertNotNull(user.getRole());
        Assert.assertTrue(user.isActive());

	}
}