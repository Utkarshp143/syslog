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
import com.advantal.userlog.model.Privilege;
import com.advantal.userlog.repositories.PrivilegeRepository;

@ExtendWith(MockitoExtension.class)
public class PrivilegeServiceImplTest {

    @Mock
    private PrivilegeRepository privilegeRepository;
    
    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private PrivilegeServiceImpl privilegeService;

    private Privilege privilege;


	@BeforeEach
	void setup() {
	    privilege = Privilege.builder()
	        .privilegeId("123")
	        .privilegeName("Sample Privilege")
	        .createdDate(new Date())
	        .updatedDate(new Date())
	        .active(true)
	        .build();	    
	}
	
    @DisplayName("JUnit test for savePrivilege method")
    @Test
    public void savePrivilege(){
    	if(privilegeRepository.findByPrivilegeName(privilege.getPrivilegeName()) == null) {
            Response response = privilegeService.createPrivilege(privilege);
            assertEquals("201",response.getStatusCode());
            assertEquals("Privilege Created Successfully!", response.getMessage());
    	}else if(privilege.getPrivilegeName().isEmpty()) {
            Response response = privilegeService.createPrivilege(privilege);
            assertEquals("400",response.getStatusCode());
            assertEquals("Privilege Name cannot be blank!", response.getMessage());
    	}
    	else {
            Response response = privilegeService.createPrivilege(privilege);
            assertEquals("409",response.getStatusCode());
            assertEquals("Privilege already Exists!", response.getMessage()); 
    	}
        
    }

}
