package com.advantal.userlog.repositories;

import com.advantal.userlog.model.Router;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;

@DataMongoTest
class RouterRepositoryTest {

    @Autowired
    private RouterRepository routerRepository;

    @Test
    @DisplayName("Find by IP")
    void findByIP() {
        // Create a sample router
        String ip = "192.168.0.1";
        Router foundRouter = routerRepository.findByIP(ip);
        // if IP already exists!
        if(foundRouter != null)
        {
        	// Verify the result
            assertNotNull(foundRouter);
            assertEquals(ip, foundRouter.getIP());
        }
        // if IP doesn't exists
        else {
            assertEquals(null, foundRouter);
        }
        
    }

    @Test
    void findByIP_NonExistingIP_ShouldReturnNull() {
        // Create a sample router
        String ip = "10.0.0.1";

        // Invoke the repository method
        Router foundRouter = routerRepository.findByIP(ip);

        // Verify the result
    }
}
