package com.advantal.userlog.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RouterGroupTest {

    @Test
    void routerGroupModelGetterSetterTest() {
        // Create a sample router group
        String routerGroupID = "123";
        String routerGroupName = "Group A";
        boolean active = true;

        // Create a RouterGroup object
        RouterGroup routerGroup = new RouterGroup();
        routerGroup.setRouterGroupID(routerGroupID);
        routerGroup.setRouterGroupName(routerGroupName);
        routerGroup.setActive(active);

        // Verify the values using getters
        assertEquals(routerGroupID, routerGroup.getRouterGroupID());
        assertEquals(routerGroupName, routerGroup.getRouterGroupName());
        assertEquals(active, routerGroup.isActive());
    }
}
