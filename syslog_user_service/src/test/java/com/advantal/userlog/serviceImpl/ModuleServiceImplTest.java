package com.advantal.userlog.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.advantal.userlog.dto.Response;
import com.advantal.userlog.model.Location;
import com.advantal.userlog.model.Privilege;
import com.advantal.userlog.model.Module;
import com.advantal.userlog.repositories.LocationRepository;
import com.advantal.userlog.repositories.ModuleRepository;

@ExtendWith(MockitoExtension.class)
public class ModuleServiceImplTest {
	@Mock
    private ModuleRepository moduleRepository;
    
    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private ModuleServiceImpl moduleService;

    private Module module;

    @BeforeEach
    void setUp() {
        Privilege privilege1 = Privilege.builder()
                .privilegeId("p1")
                .privilegeName("Privilege 1")
                .createdDate(new Date())
                .updatedDate(new Date())
                .active(true)
                .build();

            Privilege privilege2 = Privilege.builder()
                .privilegeId("p2")
                .privilegeName("Privilege 2")
                .createdDate(new Date())
                .updatedDate(new Date())
                .active(true)
                .build();
            
            Privilege[] privileges = {privilege1, privilege2};
            
            module = Module.builder()
                .moduleId("123")
                .moduleName("Sample Module")
                .createdDate(new Date())
                .updatedDate(new Date())
                .privilege(privileges)
                .active(true)
                .build();
            

    }

    // JUnit test for saveModule method
    @DisplayName("JUnit test for saveModule method")
    @Test
    public void saveModule(){
    	if(moduleRepository.findByModuleName(module.getModuleName()) == null) {
            Response response = moduleService.createModule(module);
            assertEquals("201",response.getStatusCode());
            assertEquals("Module Created Successfully!", response.getMessage());
    	}else if(module.getModuleName().isEmpty()) {
            Response response = moduleService.createModule(module);
            assertEquals("400",response.getStatusCode());
            assertEquals("Module Name cannot be blank!", response.getMessage());
    	}
    	else {
            Response response = moduleService.createModule(module);
            assertEquals("409",response.getStatusCode());
            assertEquals("Module already Exists!", response.getMessage()); 
    	}
    }
}
