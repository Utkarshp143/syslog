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
import com.advantal.userlog.dto.PrivilegeDto;
import com.advantal.userlog.dto.Response;
import com.advantal.userlog.model.Privilege;
import com.advantal.userlog.serviceImpl.PrivilegeServiceImpl;


@RestController
@RequestMapping("/privilegeApi")
public class PrivilegeController {

	@Autowired
	private PrivilegeServiceImpl privilegeService;

	@Autowired
	private ModelMapper modelMapper;
	
	
	
	// with Model
	@GetMapping("/getAllUserPrivilegeModel")
	public ResponseEntity<Page<Privilege>> getAllPrivilegeModel(
				@RequestParam(required = false) String searchTerm,
				@RequestParam(required = false) String order,
				@RequestParam(required = false) String field,
				Pageable pageable) {
			
			
			Page<Privilege> privileges = privilegeService.getAllPrivileges(pageable, searchTerm, order, field);
			return ResponseEntity.ok(privileges);
	}
		
	// with DTO
	// Get List of all Privileges
	@GetMapping("/getAllPrivileges")
	public ResponseEntity<Page<PrivilegeDto>> getAllPrivileges(
			@RequestParam(required = false) String searchTerm, 
			@RequestParam(required = false) String order,
			@RequestParam(required = false) String field, 
			Pageable pageable) {
		
		Page<Privilege> privileges = privilegeService.getAllPrivileges(pageable, searchTerm, order, field);
		List<PrivilegeDto> collect =  privileges.stream().map(mod->modelMapper.map(mod ,PrivilegeDto.class)).collect(Collectors.toList());
	    Page<PrivilegeDto> pages = new PageImpl<PrivilegeDto>(collect, pageable, collect.size());

		return ResponseEntity.ok(pages);
	}
	// Get Privilege by ID
	@GetMapping("/fetchPrivilege/{id}")
	public Response<Privilege> getPrivilegeById(@PathVariable(value = "id") String privilegeId)
			throws ResourceNotFoundException {
		return this.privilegeService.getPrivilegeById(privilegeId);
	}

	// add Privilege into the database
	@PostMapping("/savePrivilege")
	public ResponseEntity<Response>createPrivilege(@RequestBody Privilege privilege) {
		return new ResponseEntity<Response>(privilegeService.createPrivilege(privilege),HttpStatus.CREATED);
	}

	// for updating the Privilege
	@PutMapping("/updatePrivilege/{id}")
	public ResponseEntity<Privilege> updatePrivilege(@PathVariable(value = "id") String privilegeId,
			@RequestBody Privilege privilegeDetails) throws ResourceNotFoundException {
		return this.privilegeService.updatePrivilege(privilegeId, privilegeDetails);
	}

	// Delete Privilege from database
	@DeleteMapping("/deletePrivilege/{id}")
	public String deletePrivilege(@PathVariable(value = "id") String privilegeId)
			throws ResourceNotFoundException {
		return this.privilegeService.deletePrivilege(privilegeId);
	}


}