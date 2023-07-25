package com.advantal.userlog.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.advantal.userlog.CustomException.ResourceNotFoundException;
import com.advantal.userlog.dto.Response;
import com.advantal.userlog.model.User;

public interface UserService {

	public Response<User> getUserById(String userId) throws ResourceNotFoundException;

	Response createUser(User user);

	ResponseEntity<User> updateUser(String userId, User userDetails) throws ResourceNotFoundException;

	String deleteUser(String userId) throws ResourceNotFoundException;

	Page<User> getAllUsers(Pageable pageable, String searchTerm, String order,String field);
}
