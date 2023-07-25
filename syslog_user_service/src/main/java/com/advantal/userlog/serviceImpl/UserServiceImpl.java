package com.advantal.userlog.serviceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import com.advantal.userlog.CustomException.ResourceNotFoundException;
import com.advantal.userlog.dto.Response;
import com.advantal.userlog.model.Privilege;
import com.advantal.userlog.model.Router;
import com.advantal.userlog.model.User;
import com.advantal.userlog.repositories.UserRepository;
import com.advantal.userlog.services.UserService;

import lombok.AllArgsConstructor;

import org.springframework.data.domain.PageRequest;


import java.util.List;
import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MongoTemplate mongoTemplate;
	
	
	private final static Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
	
	List<String> filterArray = Arrays.asList("userId","userName","createdDate","updatedDate","role"
			,"password","contactNumber","employeeCode","department","active","additionalInfo");

	@Override
	public Page<User> getAllUsers(Pageable pageable, String searchTerm, String order,
			String filter) {
		// TODO Auto-generated method stub
        Criteria criteria = new Criteria();
        if (StringUtils.hasText(searchTerm)) {
            criteria.orOperator(
                    Criteria.where("userId").regex(".*" + searchTerm + ".*", "i"),
                    Criteria.where("userName").regex(".*" + searchTerm + ".*", "i"),
                    Criteria.where("password").regex(".*" + searchTerm + ".*", "i"),
                    Criteria.where("contactNumber").regex(".*" + searchTerm + ".*", "i"),
                    Criteria.where("employeeCode").regex(".*" + searchTerm + ".*", "i"),
                    Criteria.where("department").regex(".*" + searchTerm + ".*", "i"),
                    Criteria.where("active").regex(".*" + searchTerm + ".*", "i"),
                    Criteria.where("additionalInfo").regex(".*" + searchTerm + ".*", "i"),
                    Criteria.where("createdDate").regex(".*" + searchTerm + ".*", "i"),
                    Criteria.where("role").regex(".*" + searchTerm + ".*", "i")
            );
        }

        Query query = new Query(criteria);

	    if (StringUtils.hasText(order)) {
	        if (order.equalsIgnoreCase("asc")) {
	            query.with(Sort.by(Sort.Direction.ASC, StringUtils.hasText(filter) && filterArray.contains(filter) ? filter : "userName"));
	        } else if (order.equalsIgnoreCase("desc")) {
	            query.with(Sort.by(Sort.Direction.DESC, StringUtils.hasText(filter) && filterArray.contains(filter) ? filter : "userName"));
	        } else {
	            throw new IllegalArgumentException("Invalid value for 'order' parameter. It should be 'asc' or 'desc'.");
	        }
	    } else {
	        // If order is not provided, set the default sort order to ASCENDING (ascending)
	        query.with(Sort.by(Sort.Direction.ASC, StringUtils.hasText(filter) && filterArray.contains(filter) ? filter : "userName"));
	    }

	    query.with(pageable);
	    List<User> userList = mongoTemplate.find(query, User.class);

	    return PageableExecutionUtils.getPage(userList, pageable,
	            () -> mongoTemplate.count(query, Router.class));

        }

	@Override
	public Response<User> getUserById(@PathVariable(value = "id") String userId)
			throws ResourceNotFoundException {
		Response response = new Response();
		if(userRepository.findById(userId) != null) {
			response.setStatusCode("201");
			response.setMessage("User found!");
			response.setData(userRepository.findById(userId));
		}
		else {
			response.setStatusCode("409");
			response.setMessage("User doesn't exist");
		}
		return response;
	}
	
	
	public Optional<User> findById(String userId) {
		Optional<User> user = userRepository.findById(userId);
		return user;

	}

	@Override
	public Response createUser(User user) {
	    Response response = new Response();

	    if (user.getUserName().isEmpty()) { 
	        response.setStatusCode("400");
	        response.setMessage("Username cannot be blank!");
	        return response; 
	    }

	    try {
	     
	        User findByUserName = this.userRepository.findByUserName(user.getUserName().toLowerCase().trim());
	        if (findByUserName == null && user.getRole() != null) {
	            user.setActive(true);
	            user.setUserName(user.getUserName().toLowerCase().trim()); // Set the username to lowercase and trimmed value
	            user.setCreatedDate(new Date());
	            this.userRepository.save(user);

	            response.setStatusCode("201");
	            response.setMessage("User Created Successfully!");
	        }else if(user.getRole() == null && findByUserName == null) {
    			response.setStatusCode("409");
    			response.setMessage("Can't create User as no Role is selected!");
	        }
	        else {
	            response.setStatusCode("409");
	            response.setMessage("User already exists!");
	        }
	    } catch (Exception e) {
	        LOGGER.info(e.getMessage());
	    }
	    return response;
	}

	
	@Override
	public ResponseEntity<User> updateUser(@PathVariable(value = "id") String userId, @RequestBody User userDetails)
			throws ResourceNotFoundException {

		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found for this id : " + userId));

		user.setDepartment(userDetails.getDepartment());
		user.setUserName(userDetails.getUserName());
		user.setPassword(userDetails.getPassword());
		user.setContactNumber(userDetails.getContactNumber());
		user.setRole(userDetails.getRole());
		user.setAdditionalInfo(userDetails.getAdditionalInfo());
		user.setActive(userDetails.isActive());
		
		final User updatedUser = userRepository.save(user);

		return ResponseEntity.ok(updatedUser);
	}

	
	@Override
	public String deleteUser(@PathVariable(value = "id") String userId) throws ResourceNotFoundException {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found for this id : " + userId));
		userRepository.delete(user);

		return "User deleted Successfully!";

	}	
}