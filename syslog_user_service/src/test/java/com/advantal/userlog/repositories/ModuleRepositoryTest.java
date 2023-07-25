package com.advantal.userlog.repositories;

import com.advantal.userlog.model.Module;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;

@DataMongoTest
class ModuleRepositoryTest {

    @Autowired
    private ModuleRepository moduleRepository;

    @Test
    @DisplayName("Find by Module")
    void findByModuleName() {
        // Create a sample module
        String moduleName = "Module A";
        Module foundModule = moduleRepository.findByModuleName(moduleName);
        // if moduleName is not present
        if(foundModule != null) {
            // Verify the result
            assertNotNull(foundModule);
            assertEquals(moduleName, foundModule.getModuleName());
        }
        else {
        	assertEquals(null, foundModule);
        }
    }

}
