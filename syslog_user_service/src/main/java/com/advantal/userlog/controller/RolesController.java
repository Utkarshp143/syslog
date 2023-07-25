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
import com.advantal.userlog.dto.RolesDto;
import com.advantal.userlog.model.Roles;
import com.advantal.userlog.serviceImpl.RolesServiceImpl;



@RestController
@RequestMapping("/roleApi")
public class RolesController {


	@Autowired
	private RolesServiceImpl rolesService;
	
	@Autowired
	private ModelMapper modelMapper;

	// with Model
		@GetMapping("/getAllRolesModel")
		public ResponseEntity<Page<Roles>> getAllRoleModel(
				@RequestParam(required = false) String searchTerm,
				@RequestParam(required = false) String order,
				@RequestParam(required = false) String field,
				Pageable pageable) {

			Page<Roles> roles = rolesService.getAllRoles(pageable, searchTerm, order, field);
			return ResponseEntity.ok(roles);
		}
		
	// with DTO
	// Get List of all Roles
		@GetMapping("/getAllRoles")
		public ResponseEntity<Page<RolesDto>> getAllRoles(
				@RequestParam(required = false) String searchTerm, 
				@RequestParam(required = false) String order,
				@RequestParam(required = false) String field, 
				Pageable pageable) {
			
			Page<Roles> roles = rolesService.getAllRoles(pageable, searchTerm, order, field);
			List<RolesDto> collect = roles.stream().map(mod->modelMapper.map(mod ,RolesDto.class)).collect(Collectors.toList());
		    Page<RolesDto> pages = new PageImpl<RolesDto>(collect, pageable, collect.size());

			return ResponseEntity.ok(pages);
		}
	// Get role by id
	@GetMapping("/fetchRole/{id}")
	public Response<Roles> getRoleById(@PathVariable(value = "id") String roleId)
			throws ResourceNotFoundException {
		return this.rolesService.getRoleById(roleId);
	}

	// add role into the database
	@PostMapping("/saveRole")
	public ResponseEntity<Response>createRole(@RequestBody Roles role) {
		return new ResponseEntity<Response>(rolesService.createRole(role),HttpStatus.CREATED);
	}

	// for updating the role
	@PutMapping("/updateRole/{id}")
	public ResponseEntity<Roles> updateRoles(@PathVariable(value = "id") String roleId,
			@RequestBody Roles roleDetails) throws ResourceNotFoundException {
		return this.rolesService.updateRole(roleId, roleDetails);
	}

	// Delete role from database
	@DeleteMapping("/deleteRole/{id}")
	public String deleteRole(@PathVariable(value = "id") String roleId)
			throws ResourceNotFoundException {
		return this.rolesService.deleteRole(roleId);
	}

}
