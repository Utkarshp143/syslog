package com.advantal.userlog.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.advantal.userlog.dto.Response;
import com.advantal.userlog.model.RouterGroup;
import com.advantal.userlog.repositories.RouterGroupRepository;


@ExtendWith(MockitoExtension.class)
public class RouterGroupServiceImplTest {


    @Mock
    private RouterGroupRepository routerGroupRepository;
    
    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private RouterGroupServiceImpl routerGroupService;

    private RouterGroup routerGroup;

	@BeforeEach
	void setup() {
	    routerGroup = RouterGroup.builder()
	        .routerGroupID("rg1")
	        .routerGroupName("Sample Router Group")
	        .active(true)
	        .build();
	    
	}
    
    // JUnit test for saveRouterGroup method
    @DisplayName("JUnit test for saveRouterGroup method")
    @Test
    public void saveRouterGroup(){
    	if(routerGroupRepository.findByRouterGroupName(routerGroup.getRouterGroupName()) == null) {
            Response response = routerGroupService.createRouterGroup(routerGroup);
            assertEquals("201",response.getStatusCode());
            assertEquals("RouterGroup Created Successfully!", response.getMessage());
    	}else if(routerGroup.getRouterGroupName().isEmpty()) {
            Response response = routerGroupService.createRouterGroup(routerGroup);
            assertEquals("400",response.getStatusCode());
            assertEquals("RouterGroup Name cannot be blank!", response.getMessage());
    	}
    	else {
            Response response = routerGroupService.createRouterGroup(routerGroup);
            assertEquals("409",response.getStatusCode());
            assertEquals("RouterGroup already Exists!", response.getMessage()); 
    	}
        
    }
	
}