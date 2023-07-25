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
import com.advantal.userlog.model.Router;
import com.advantal.userlog.model.RouterGroup;
import com.advantal.userlog.repositories.RouterGroupRepository;
import com.advantal.userlog.repositories.RouterRepository;

@ExtendWith(MockitoExtension.class)
public class RouterServiceImplTest {


    @Mock
    private RouterRepository routerRepository;
    
    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private RouterServiceImpl routerService;

    private Router router;

	@BeforeEach
	void setup() {
		
        // Create a Location instance
        Location location = Location.builder()
                .locationID("1")
                .locationName("Sample Location")
                .build();

        // Create a RouterGroup instance
        RouterGroup routerGroup = RouterGroup.builder()
                .routerGroupID("rg1")
                .routerGroupName("Sample Router Group")
                .active(true)
                .build();

        // Create a Router instance

		
        router = Router.builder()
                .id("router1")
                .IP("192.168.0.1")
                .host("sample-host")
                .location(location) 
                .routerGroup(routerGroup) 
                .lastMessageSent(new Date())
                .build();
	}
    
    // JUnit test for saveRouter method
    @DisplayName("JUnit test for saveRouter method")
    @Test
    public void saveRouter(){
    	if(routerRepository.findByIP(router.getIP()) == null) {
            Response response = routerService.createRouter(router);
            assertEquals("201",response.getStatusCode());
            assertEquals("Router Created Successfully!", response.getMessage());
    	}else if(router.getIP().isEmpty()) {
            Response response = routerService.createRouter(router);
            assertEquals("400",response.getStatusCode());
            assertEquals("Router Name cannot be blank!", response.getMessage());
    	}
    	else {
            Response response = routerService.createRouter(router);
            assertEquals("409",response.getStatusCode());
            assertEquals("Router already Exists!", response.getMessage()); 
    	}
        
    }

	
}