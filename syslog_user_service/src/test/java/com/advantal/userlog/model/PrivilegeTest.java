package com.advantal.userlog.model;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

@DisplayName("PrivilegeTest")
public class PrivilegeTest {

    @Test
    @DisplayName("Test the values")
    public void testPrivilege() {
        // Create a Privilege object
        Privilege privilege = new Privilege();
        privilege.setPrivilegeId("123");
        privilege.setPrivilegeName("Test Privilege");
        privilege.setCreatedDate(new Date());
        privilege.setUpdatedDate(new Date());
        privilege.setActive(true);

        Assert.assertEquals("123", privilege.getPrivilegeId());
        Assert.assertEquals("Test Privilege", privilege.getPrivilegeName());
        Assert.assertNotNull(privilege.getCreatedDate());
        Assert.assertNotNull(privilege.getUpdatedDate());
        Assert.assertTrue(privilege.isActive());
    }
}
