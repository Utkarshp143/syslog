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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.advantal.userlog.CustomException.ResourceNotFoundException;
import com.advantal.userlog.dto.Response;
import com.advantal.userlog.model.Privilege;
import com.advantal.userlog.model.Roles;
import com.advantal.userlog.model.Router;
import com.advantal.userlog.model.User;
import com.advantal.userlog.repositories.RolesRepository;
import com.advantal.userlog.services.RolesService;

@Service
public class RolesServiceImpl implements RolesService{
	
	@Autowired
    private RolesRepository rolesRepository;
	@Autowired
	private MongoTemplate mongoTemplate;
	
	private final static Logger LOGGER = LoggerFactory.getLogger(RolesServiceImpl.class);

	List<String> filterArray = Arrays.asList("roleId","roleName","createdDate","updatedDate","module");


	@Override
	public Page<Roles> getAllRoles(Pageable pageable, String searchTerm, String order, String filter) {
	    Criteria criteria = new Criteria();
	    if (StringUtils.hasText(searchTerm)) {
	        criteria.orOperator(
	                Criteria.where("roleId").regex(".*" + searchTerm + ".*", "i"),
	                Criteria.where("roleName").regex(".*" + searchTerm + ".*", "i"),
	                Criteria.where("updatedDate").regex(".*" + searchTerm + ".*", "i"),
	                Criteria.where("createdDate").regex(".*" + searchTerm + ".*", "i"),
	                Criteria.where("module").regex(".*" + searchTerm + ".*", "i")
	        );
	    }

	    Query query = new Query(criteria);

	    if (StringUtils.hasText(order)) {
	        if (order.equalsIgnoreCase("asc")) {
	            query.with(Sort.by(Sort.Direction.ASC, StringUtils.hasText(filter) && filterArray.contains(filter) ? filter : "roleName"));
	        } else if (order.equalsIgnoreCase("desc")) {
	            query.with(Sort.by(Sort.Direction.DESC, StringUtils.hasText(filter) && filterArray.contains(filter) ? filter : "roleName"));
	        } else {
	            throw new IllegalArgumentException("Invalid value for 'order' parameter. It should be 'asc' or 'desc'.");
	        }
	    } else {
	        // If order is not provided, set the default sort order to ASCENDING (ascending)
	        query.with(Sort.by(Sort.Direction.ASC, StringUtils.hasText(filter) && filterArray.contains(filter) ? filter : "roleName"));
	    }

	    query.with(pageable);
	    List<Roles> roleList = mongoTemplate.find(query, Roles.class);

	    return PageableExecutionUtils.getPage(roleList, pageable,
	            () -> mongoTemplate.count(query, Roles.class));

	}

	
	
    public Response<Roles> getRoleById(@PathVariable(value = "id") String roleId)
            throws ResourceNotFoundException {
    	Response response = new Response();
    	if(rolesRepository.findById(roleId) != null) {
    		response.setStatusCode(roleId);
    		response.setMessage("Role found!");
    		response.setData(rolesRepository.findById(roleId));
    	}
    	else {
    		response.setStatusCode(roleId);
    		response.setMessage("Role doesn't exist");
    	}
    	return response;
    }


    public Optional<Roles> findById(String roleId) {
        Optional<Roles> role = rolesRepository.findById(roleId);
        return  role;

    }

    public Response createRole(Roles role) {
    	Response response = new Response();
    	try {
    		Roles findByRolesName = this.rolesRepository.findByRoleName(role.getRoleName().toLowerCase().trim());
    		if(findByRolesName == null && role.getModule() != null) {
    			role.setRoleName(role.getRoleName().toLowerCase().trim());
    			role.setActive(true);
    			role.setCreatedDate(new Date());
    			role.setUpdatedDate(new Date());
    			role.setModule(role.getModule());
    			this.rolesRepository.save(role);
    			
    			response.setStatusCode("201");
    			response.setMessage("Role Created Successfully!");
    		}else if(role.getModule() == null && findByRolesName == null) {
    			response.setStatusCode("409");
    			response.setMessage("Can't create role as no module is selected!");
    		}
    		
    		else {
    			response.setStatusCode("409");
    			response.setMessage("Role already Exists");
    		}
    	}catch(Exception e) {
    		LOGGER.info(e.getMessage());
    		response.setMessage("Exception Occurred");
    	}return response;
    }

    public ResponseEntity<Roles> updateRole(@PathVariable(value = "id") String roleId,
                                                   @RequestBody Roles roleDetails) throws ResourceNotFoundException {

    	Roles role = rolesRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Roles not found for this id : " + roleId));

    	role.setRoleName(roleDetails.getRoleName());
    	role.setModule(roleDetails.getModule());
    	role.setUpdatedDate(new Date());
        final Roles updatedRole = rolesRepository.save(role);

        return ResponseEntity.ok(updatedRole);
    }


    public String deleteRole(@PathVariable(value = "id") String roleId)
            throws ResourceNotFoundException {
        Roles role = rolesRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Roles not found for this id : " + roleId));
        rolesRepository.delete(role);

		return "Role deleted Successfully!";

           }

}
