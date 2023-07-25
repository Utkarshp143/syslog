package com.advantal.userlog.repositories;

import com.advantal.userlog.model.RouterGroup;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;

@DataMongoTest
class RouterGroupRepositoryTest {

    @Autowired
    private RouterGroupRepository routerGroupRepository;

    @Test
    @DisplayName("Find by RouterGroup")
    void findByRouterGroupName() {
        // Create a sample router group
        String routerGroupName = "Group A";
        RouterGroup foundRouterGroup = routerGroupRepository.findByRouterGroupName(routerGroupName);
        // if routerGroup already exists!
        if(foundRouterGroup != null) {
            // Verify the result
            assertNotNull(foundRouterGroup);
            assertEquals(routerGroupName, foundRouterGroup.getRouterGroupName());
        }
     // if routerGroup doesn't exists!
        else {
        	assertEquals(null, foundRouterGroup);
        }
    }

}
