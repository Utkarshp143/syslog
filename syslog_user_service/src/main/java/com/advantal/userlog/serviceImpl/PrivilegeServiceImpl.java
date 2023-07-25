package com.advantal.userlog.serviceImpl;

import java.util.Arrays;
import java.util.Date;
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
import com.advantal.userlog.repositories.PrivilegeRepository;
import com.advantal.userlog.services.PrivilegeService;

@Service
public class PrivilegeServiceImpl implements PrivilegeService {

	@Autowired
	private PrivilegeRepository privilegeRepository;
	@Autowired
	private MongoTemplate mongoTemplate;

	private final static Logger LOGGER = LoggerFactory.getLogger(PrivilegeServiceImpl.class);
	
	List<String> filterArray = Arrays.asList("privilegeId","privilegeName","createdDate","updatedDate");

	@Override
	public Page<Privilege> getAllPrivileges(Pageable pageable, String searchTerm, String order, String filter) {
	    Criteria criteria = new Criteria();
	    if (StringUtils.hasText(searchTerm)) {
	        criteria.orOperator(
	            Criteria.where("privilegeId").regex(".*" + searchTerm + ".*", "i"),
	            Criteria.where("privilegeName").regex(".*" + searchTerm + ".*", "i"),
	            Criteria.where("createdDate").regex(".*" + searchTerm + ".*", "i"),
	            Criteria.where("updatedDate").regex(".*" + searchTerm + ".*", "i")
	        );
	    }

	    Query query = new Query(criteria);

	    if (StringUtils.hasText(order)) {
	        if (order.equalsIgnoreCase("asc")) {
	            query.with(Sort.by(Sort.Direction.ASC, StringUtils.hasText(filter) && filterArray.contains(filter) ? filter : "privilegeName"));
	        } else if (order.equalsIgnoreCase("desc")) {
	            query.with(Sort.by(Sort.Direction.DESC, StringUtils.hasText(filter) && filterArray.contains(filter) ? filter : "privilegeName"));
	        } else {
	            throw new IllegalArgumentException("Invalid value for 'order' parameter. It should be 'asc' or 'desc'.");
	        }
	    } else {
	        // If order is not provided, set the default sort order to ASCENDING (ascending)
	        query.with(Sort.by(Sort.Direction.ASC, StringUtils.hasText(filter) && filterArray.contains(filter) ? filter : "privilegeName"));
	    }

	    query.with(pageable);
	    List<Privilege> privilegeList = mongoTemplate.find(query, Privilege.class);

	    return PageableExecutionUtils.getPage(privilegeList, pageable,
	            () -> mongoTemplate.count(query, Privilege.class));
	}

	
	
//	@Override
//	public Page<Privilege> getAllPrivileges(Pageable pageable, String searchTerm, String order, String filter) {
//	    Criteria criteria = new Criteria();
//	    if (StringUtils.hasText(searchTerm)) {
//	        criteria.orOperator(
//	            Criteria.where("privilegeId").regex(".*" + searchTerm + ".*", "i"),
//	            Criteria.where("privilegeName").regex(".*" + searchTerm + ".*", "i"),
//	            Criteria.where("createdDate").regex(".*" + searchTerm + ".*", "i"),
//	            Criteria.where("updatedDate").regex(".*" + searchTerm + ".*", "i")
//	        );
//	    }
//
//	    Query query = new Query(criteria);
//
//	    if (StringUtils.hasText(order) && (order.equalsIgnoreCase("asc") || order.equalsIgnoreCase("desc"))) {
//	        Sort.Direction sortDirection = Sort.Direction.ASC;
//	        if (order.equalsIgnoreCase("desc")) {
//	            sortDirection = Sort.Direction.DESC;
//	        }
//
//	        if (StringUtils.hasText(filter) && filterArray.contains(filter)) {
//	            query.with(Sort.by(sortDirection, filter));
//	        } else {
//	            // default filter
//	            query.with(Sort.by(sortDirection, "privilegeName"));
//	        }
//	    } else if(order == null){
//	        // If order is not provided, set the default sort order to ASCENDING (ascending)
//	        query.with(Sort.by(Sort.Direction.ASC, "privilegeName"));
//	    }
//	    else {
//	    	return Page.empty();
//	    }
//
//	    query.with(pageable);
//	    List<Privilege> privilegeList = mongoTemplate.find(query, Privilege.class);
//
//	    return PageableExecutionUtils.getPage(privilegeList, pageable,
//	            () -> mongoTemplate.count(query, Router.class));
//	}

	
	
	public Optional<Privilege> findById(String privilegeId) {
		
		// TODO Auto-generated method stub
		return privilegeRepository.findById(privilegeId);
	}

	@Override
	public Response<Privilege> getPrivilegeById(String privilegeId) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		Response response = new Response();
		if(privilegeRepository.findById(privilegeId) != null) {
			response.setStatusCode("201");
			response.setMessage("Privilege Found!");
			response.setData(privilegeRepository.findById(privilegeId));
		}
		else {
			response.setMessage("Privilege doesn't exit!");
			response.setStatusCode(privilegeId);
		}
		return response;

	}

	@Override
	public Response createPrivilege(Privilege privilege) {
		
		 Response response = new Response();
		 
		 
		 try {
		
		 Privilege findByPrivilegeName = this.privilegeRepository.findByPrivilegeName(privilege.getPrivilegeName().toLowerCase().trim());
		 
		if(findByPrivilegeName == null) {
			
			 privilege.setActive(true);
			 privilege.setPrivilegeName(privilege.getPrivilegeName().toLowerCase().trim());
			 privilege.setCreatedDate(new Date());
		 	 privilege.setUpdatedDate(new Date());
		 	 this.privilegeRepository.save(privilege);
		 	  response.setStatusCode("201");
		 	  response.setMessage("Privilege Created Successfully!");
		 	 }
		 	 else {
			 	response.setStatusCode("409");
		 		response.setMessage("Privilege Already exists!");
		 	 }
		 }catch(Exception e) {
			 LOGGER.info(e.getMessage()); 
			 response.setMessage("Exception Occurred!");
		 }
		 return response;
	}

	@Override
	public ResponseEntity<Privilege> updatePrivilege(String privilegeId, Privilege privilegeDetails)
			throws ResourceNotFoundException {
		
		// TODO Auto-generated method stub
		Privilege privilege = privilegeRepository.findById(privilegeId)
				.orElseThrow(() -> new ResourceNotFoundException("Privilege not found for this id : " + privilegeId));

		privilege.setPrivilegeName(privilegeDetails.getPrivilegeName());
		privilege.setUpdatedDate(new Date());

		final Privilege updatedPrivilege = privilegeRepository.save(privilege);

		return ResponseEntity.ok(updatedPrivilege);

	}

	@Override
	public String deletePrivilege(String privilegeId) throws ResourceNotFoundException {
		
		// TODO Auto-generated method stub
		Privilege privilege = privilegeRepository.findById(privilegeId)
				.orElseThrow(() -> new ResourceNotFoundException("Privilege not found for this id : " + privilegeId));
		privilegeRepository.delete(privilege);
		return "Privilege deleted Successfully!";


	}

}























