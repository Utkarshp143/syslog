package com.advantal.userlog.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.advantal.userlog.CustomException.ResourceNotFoundException;
import com.advantal.userlog.dto.Response;
import com.advantal.userlog.dto.UserDto;
import com.advantal.userlog.model.User;
import com.advantal.userlog.serviceImpl.UserServiceImpl;


@RestController
@RequestMapping("/userApi")
public class UserController {

	@Autowired
	private UserServiceImpl userService;

	@Autowired
	private ModelMapper modelMapper;
	
	// with Model
	@GetMapping("/getAllUserModel")
	public ResponseEntity<Page<User>> getAllUserModel(
			@RequestParam(required = false) String searchTerm,
			@RequestParam(required = false) String order,
			@RequestParam(required = false) String field,
			Pageable pageable) {
		
		
		Page<User> users = userService.getAllUsers(pageable, searchTerm, order, field);
		return ResponseEntity.ok(users);
	}
	
	// with DTO
	@GetMapping("/getAllUsers")
	public ResponseEntity<Page<UserDto>> getAllUsers(
			@RequestParam(required = false) String searchTerm,
			@RequestParam(required = false) String order,
			@RequestParam(required = false) String field,
			Pageable pageable) {
		
		
		Page<User> users = userService.getAllUsers(pageable, searchTerm, order, field);
		List<UserDto> collect = users.stream().map(mod->modelMapper.map(mod,UserDto.class)).collect(Collectors.toList());
	    Page<UserDto> pages = new PageImpl<UserDto>(collect, pageable, collect.size());

		return ResponseEntity.ok(pages);
	}
	
	// Get user by id
	@GetMapping("/getUser/{id}")
	public Response<User> getUserById(@PathVariable(value = "id") String userId)
			throws ResourceNotFoundException {
		
		return this.userService.getUserById(userId);
	}

	// add user into the database
	@PostMapping("/saveUser")
	public ResponseEntity<Response>createUser(@RequestBody User user) {
		return new ResponseEntity<Response>(userService.createUser(user),HttpStatus.CREATED);
	}

	// for updating the user
	@PutMapping("/updateUser/{id}")
	public ResponseEntity<User> updateUser(@PathVariable(value = "id") String userId, @RequestBody User userDetails)
			throws ResourceNotFoundException {
		return this.userService.updateUser(userId, userDetails);
	}

	// Delete user from database
	@DeleteMapping("/deleteUser/{id}")
	public String deleteUser(@PathVariable(value = "id") String userId) throws ResourceNotFoundException {
		return this.userService.deleteUser(userId);
	}

}
