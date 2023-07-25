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
import com.advantal.userlog.model.Router;
import com.advantal.userlog.model.User;
import com.advantal.userlog.repositories.RouterRepository;
import com.advantal.userlog.services.RouterService;
import com.advantal.userlog.dto.Response;


@Service
public class RouterServiceImpl implements RouterService{

	@Autowired
	private RouterRepository routerRepository;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	private final static Logger LOGGER = LoggerFactory.getLogger(PrivilegeServiceImpl.class);

	List<String> filterArray = Arrays.asList("id","IP","host","location","routerGroup","lastMessageSent");

	@Override
	public Page<Router> getAllRouters(Pageable pageable, String searchTerm, String order, String filter) {
	    Criteria criteria = new Criteria();
	    if (StringUtils.hasText(searchTerm)) {
	        criteria.orOperator(
	                Criteria.where("id").regex(".*" + searchTerm + ".*", "i"),
	                Criteria.where("IP").regex(".*" + searchTerm + ".*", "i"),
	                Criteria.where("host").regex(".*" + searchTerm + ".*", "i"),
	                Criteria.where("location").regex(".*" + searchTerm + ".*", "i"),
	                Criteria.where("routerGroup").regex(".*" + searchTerm + ".*", "i"),
	                Criteria.where("lastMessageSent").regex(".*" + searchTerm + ".*", "i")
	        );
	    }

	    Query query = new Query(criteria);

	    if (StringUtils.hasText(order)) {
	        if (order.equalsIgnoreCase("asc")) {
	            query.with(Sort.by(Sort.Direction.ASC, StringUtils.hasText(filter) && filterArray.contains(filter) ? filter : "IP"));
	        } else if (order.equalsIgnoreCase("desc")) {
	            query.with(Sort.by(Sort.Direction.DESC, StringUtils.hasText(filter) && filterArray.contains(filter) ? filter : "IP"));
	        } else {
	            throw new IllegalArgumentException("Invalid value for 'order' parameter. It should be 'asc' or 'desc'.");
	        }
	    } else {
	        // If order is not provided, set the default sort order to ASCENDING (ascending)
	        query.with(Sort.by(Sort.Direction.ASC, StringUtils.hasText(filter) && filterArray.contains(filter) ? filter : "IP"));
	    }

	    query.with(pageable);
	    List<Router> routerList = mongoTemplate.find(query, Router.class);

	    return PageableExecutionUtils.getPage(routerList, pageable,
	            () -> mongoTemplate.count(query, Router.class));

	}


	public Optional<Router> findById(String routerId) {
		// TODO Auto-generated method stub
		Optional<Router> router = routerRepository.findById(routerId);
        return  router;	
    }

	@Override
	public Response<Router> getRouterById(String routerId) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		Response response = new Response();
		if(routerRepository.findById(routerId) != null) {
			response.setMessage("Router found!");
			response.setData(routerRepository.findById(routerId));
			response.setStatusCode("201");
		}
		else {
			response.setStatusCode(routerId);
			response.setMessage("Router doesn't exist");
		}
		return response;
	}

	@Override
	public Response createRouter(Router router) {
		// TODO Auto-generated method stub
		Response response = new Response();
		try {
			Router findByIP = this.routerRepository.findByIP(router.getIP().trim());
			if(findByIP == null) {
				router.setHost(router.getHost());
				router.setIP(router.getIP().trim());
				router.setLocation(router.getLocation());
				router.setLastMessageSent(router.getLastMessageSent());
				router.setRouterGroup(router.getRouterGroup());
				this.routerRepository.save(router);
				response.setStatusCode("201");
				response.setMessage("Router Created Successfully!");
			}
			else {
				response.setStatusCode("409");
				response.setMessage("Router already exists!");
			}
		}catch(Exception e) {
			LOGGER.info(e.getMessage());
			response.setMessage("Exception Occurred!");
		}return response;
	}

	@Override
	public ResponseEntity<Router> updateRouter(String routerId, Router routerDetails) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		Router router = routerRepository.findById(routerId)
                .orElseThrow(() -> new ResourceNotFoundException("Router not found for this id : " + routerId));

		//router.setIP(router.getIP());
		router.setHost(router.getHost());
		router.setLocation(router.getLocation());
		router.setRouterGroup(router.getRouterGroup());
		router.setLastMessageSent(router.getLastMessageSent());
        final Router updatedRouter = routerRepository.save(router);

        return ResponseEntity.ok(updatedRouter);	}

	@Override
	public String deleteRouter(String routerId) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		  Router router = routerRepository.findById(routerId)
	                .orElseThrow(() -> new ResourceNotFoundException("Router not found for this id : " + routerId));
	        routerRepository.delete(router);

    		return "Router deleted Successfully!";

	}

}
