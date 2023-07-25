package com.advantal.userlog.serviceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.advantal.userlog.CustomException.ResourceNotFoundException;
import com.advantal.userlog.dto.Response;
import com.advantal.userlog.model.Privilege;
import com.advantal.userlog.model.Router;
import com.advantal.userlog.model.RouterGroup;
import com.advantal.userlog.repositories.RouterGroupRepository;
import com.advantal.userlog.services.RouterGroupService;


@Service
public class RouterGroupServiceImpl implements RouterGroupService{
	
	@Autowired
	private RouterGroupRepository routerGroupRepository;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	private final static Logger LOGGER = LoggerFactory.getLogger(RouterGroupServiceImpl.class);

	List<String> filterArray = Arrays.asList("routerGroupId","routerGroupName");

	
	@Override
	public Page<RouterGroup> getAllRouterGroups(Pageable pageable, String searchTerm, String order,
			String filter) {
		// TODO Auto-generated method stub
        Criteria criteria = new Criteria();
        if (StringUtils.hasText(searchTerm)) {
            criteria.orOperator(
            		
                    Criteria.where("routerGroupId").regex(".*" + searchTerm + ".*", "i"),
                    Criteria.where("routerGroupName").regex(".*" + searchTerm + ".*", "i")
            );
        }

        Query query = new Query(criteria);

	    if (StringUtils.hasText(order)) {
	        if (order.equalsIgnoreCase("asc")) {
	            query.with(Sort.by(Sort.Direction.ASC, StringUtils.hasText(filter) && filterArray.contains(filter) ? filter : "routerGroupName"));
	        } else if (order.equalsIgnoreCase("desc")) {
	            query.with(Sort.by(Sort.Direction.DESC, StringUtils.hasText(filter) && filterArray.contains(filter) ? filter : "routerGroupName"));
	        } else {
	            throw new IllegalArgumentException("Invalid value for 'order' parameter. It should be 'asc' or 'desc'.");
	        }
	    } else {
	        // If order is not provided, set the default sort order to ASCENDING (ascending)
	        query.with(Sort.by(Sort.Direction.ASC, StringUtils.hasText(filter) && filterArray.contains(filter) ? filter : "routerGroupName"));
	    }

	    query.with(pageable);
	    List<RouterGroup> routerGroupList = mongoTemplate.find(query, RouterGroup.class);

	    return PageableExecutionUtils.getPage(routerGroupList, pageable,
	            () -> mongoTemplate.count(query, RouterGroup.class));
	    }

	@Override
	public Response<RouterGroup> getRouterGroupById(String routerGroupId) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
    	Response response = new Response();
		if(routerGroupRepository.findById(routerGroupId) != null) {
    		response.setMessage("RouterGroup found!");
    		response.setStatusCode("201");
    		response.setData(routerGroupRepository.findById(routerGroupId));
    	}
		else {
			response.setMessage("RouterGroup doesn't exist");
			response.setStatusCode("409");
		}
		return response;
	}

	@Override
	public String deleteRouterGroup(String routerGroupId) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		RouterGroup routerGroup = routerGroupRepository.findById(routerGroupId)
                .orElseThrow(() -> new ResourceNotFoundException("Router Group not found for this id : " + routerGroupId));
		routerGroupRepository.delete(routerGroup);

		return "Router Group deleted Successfully!";

	}

	@Override
	public Response createRouterGroup(RouterGroup routerGroup) {
		// TODO Auto-generated method stub
		Response response = new Response();
		try {
			RouterGroup findByRouterGroupName = this.routerGroupRepository.findByRouterGroupName(routerGroup.getRouterGroupName().toLowerCase().trim());
			if(findByRouterGroupName == null) {
				routerGroup.setActive(true);
				routerGroup.setRouterGroupName(routerGroup.getRouterGroupName().toLowerCase().trim());
				this.routerGroupRepository.save(routerGroup);
				response.setStatusCode("201");
				response.setMessage("RouterGroup Created Successfully!");
			}
			else {
				response.setStatusCode("409");
				response.setMessage("RouterGroup already exists!");
			}
		}catch(Exception e) {
			LOGGER.info(e.getMessage());
			response.setMessage("Exception Occured");
		}return response;
	}

	@Override
	public ResponseEntity<RouterGroup> updateRouterGroup(String routerGroupId, RouterGroup routerGroupDetails)
			throws ResourceNotFoundException {
		// TODO Auto-generated method stub

		RouterGroup routerGroup = routerGroupRepository.findById(routerGroupId)
                .orElseThrow(() -> new ResourceNotFoundException("RouterGroup not found for this id : " + routerGroupId));

    	
		routerGroup.setRouterGroupName(routerGroupDetails.getRouterGroupName());
		
        final RouterGroup updatedRouterGroup = routerGroupRepository.save(routerGroup);

        return ResponseEntity.ok(updatedRouterGroup);
	}

	public Optional<RouterGroup> findById(String routerGroupId) {
		// TODO Auto-generated method stub
        Optional<RouterGroup> routerGroup = routerGroupRepository.findById(routerGroupId);
        return  routerGroup;
	}

}
