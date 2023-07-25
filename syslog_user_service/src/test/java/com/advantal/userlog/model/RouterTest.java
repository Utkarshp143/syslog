package com.advantal.userlog.model;

import java.util.Date;

import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


@DisplayName("RouterTest")
public class RouterTest {
	
	@Test
	@DisplayName("test the values")
	public void testRouter() {
		// create the object of router
		Router router = new Router();
		router.setHost("Test Router");
		router.setId("123");
		router.setIP("192.168.0.0");
		router.setLastMessageSent(new Date());
		router.setLocation(new Location());
		router.setRouterGroup(new RouterGroup());
		
		// Verify the values are set correctly
        Assert.assertEquals("123", router.getId());
        Assert.assertEquals("192.168.0.0", router.getIP());
        Assert.assertEquals("Test Router", router.getHost());
        Assert.assertNotNull(router.getLocation());
        Assert.assertNotNull(router.getRouterGroup());
        Assert.assertNotNull(router.getLastMessageSent());
}
}