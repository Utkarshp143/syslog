package com.advantal.userlog.model;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

@DisplayName("ModuleTest")
public class ModuleTest {
	
	 	@Test
	    @DisplayName("Test the values")
	    public void testModule() {


	        // Create a Module object
	 		Module module = new Module();

	        module.setModuleId("123");
	        module.setModuleName("Test Module");
	        module.setCreatedDate(new Date());
	        module.setUpdatedDate(new Date());
	        module.setActive(true);
	        Privilege[] privileges = {new Privilege(), new Privilege()};
	        module.setPrivilege(privileges);
	        
	        Assert.assertEquals("123", module.getModuleId());
	        Assert.assertEquals("Test Module", module.getModuleName());
	        Assert.assertNotNull(module.getCreatedDate());
	        Assert.assertNotNull(module.getUpdatedDate());
	        Assert.assertTrue(module.isActive());
	        Assert.assertArrayEquals(privileges, module.getPrivilege());

	    }
	 }


