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
import com.advantal.userlog.model.Department;
import com.advantal.userlog.model.Roles;
import com.advantal.userlog.model.Module;
import com.advantal.userlog.model.Privilege;
import com.advantal.userlog.repositories.DepartmentRepository;
import com.advantal.userlog.repositories.RolesRepository;

@ExtendWith(MockitoExtension.class)
public class RolesServiceImplTest {
    @Mock
    private RolesRepository rolesRepository;
    
    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private RolesServiceImpl rolesService;

    private Roles roles;

    @BeforeEach
    void setup() {
        Module module1 = Module.builder()
            .moduleId("m1")
            .moduleName("Module 1")
            .createdDate(new Date())
            .updatedDate(new Date())
            .active(true)
            .build();

        Module module2 = Module.builder()
            .moduleId("m2")
            .moduleName("Module 2")
            .createdDate(new Date())
            .updatedDate(new Date())
            .privilege(new Privilege[0])
            .active(true)
            .build();

        Module[] modules = {module1, module2};

        roles = Roles.builder()
            .roleId("r1")
            .roleName("Sample Role")
            .createdDate(new Date())
            .updatedDate(new Date())
            .module(modules)
            .active(true)
            .build();
        
    }
    // JUnit test for saveRole method
    @DisplayName("JUnit test for saveDepartment method")
    @Test
    public void saveRoles(){
    	if(rolesRepository.findByRoleName(roles.getRoleName()) == null) {
            Response response = rolesService.createRole(roles);
            assertEquals("201",response.getStatusCode());
            assertEquals("Role Created Successfully!", response.getMessage());
    	}else if(roles.getRoleName().isEmpty()) {
            Response response = rolesService.createRole(roles);
            assertEquals("400",response.getStatusCode());
            assertEquals("Role Name cannot be blank!", response.getMessage());
    	}
    	else {
            Response response = rolesService.createRole(roles);
            assertEquals("409",response.getStatusCode());
            assertEquals("Role already Exists!", response.getMessage()); 
    	}
        
    }
}