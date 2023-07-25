package com.advantal.userlog.model;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

@DisplayName("RolesTest")
public class RolesTest {

	@Test
    @DisplayName("Test the values")
    public void testRoles() {


        // Create a Role object
		Roles role = new Roles();

		role.setRoleId("123");
        role.setRoleName("Test Role");
        role.setCreatedDate(new Date());
        role.setUpdatedDate(new Date());
        role.setActive(true);
        Module[] modules = {new Module(), new Module()};
        role.setModule(modules);
        
        Assert.assertEquals("123", role.getRoleId());
        Assert.assertEquals("Test Role", role.getRoleName());
        Assert.assertNotNull(role.getCreatedDate());
        Assert.assertNotNull(role.getUpdatedDate());
        Assert.assertTrue(role.isActive());
        Assert.assertArrayEquals(modules, role.getModule());

    }
}
