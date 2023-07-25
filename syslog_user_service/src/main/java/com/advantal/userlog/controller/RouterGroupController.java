package com.advantal.userlog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import com.advantal.userlog.model.RouterGroup;
import com.advantal.userlog.serviceImpl.RouterGroupServiceImpl;


@RestController
@RequestMapping("routerGroupApi")
public class RouterGroupController {


	@Autowired
	private RouterGroupServiceImpl routerGroupService;
	
	
	
	// WITH MODEL
	// Get List of all RouterGroups
	@GetMapping("/getAllRouterGroups")
	public ResponseEntity<Page<RouterGroup>> getAllRouterGroups(
			@RequestParam(required = false) String searchTerm, 
			@RequestParam(required = false) String order,
			@RequestParam(required = false) String field, 
			Pageable pageable) {
		
		Page<RouterGroup> routerGroups = routerGroupService.getAllRouterGroups(pageable, searchTerm, order, field);
		
		return ResponseEntity.ok(routerGroups);
	}
	
//	// WITH DTO
//	// Get List of all RouterGroups
//	@GetMapping("/getAllRouterGroups")
//	public ResponseEntity<Page<RouterGroupDto>> getAllRouterGroups(
//			@RequestParam(required = false) String searchTerm, 
//			@RequestParam(required = false) String order,
//			@RequestParam(required = false) String field, 
//			Pageable pageable) {
//		
//		Page<RouterGroup> routerGroups = routerGroupService.getAllRouterGroups(pageable, searchTerm, order, field);
//		List<RouterGroupDto> collect = routerGroups.stream().map(mod->modelMapper.map(mod, RouterGroupDto.class)).collect(Collectors.toList());
//		Page<RouterGroupDto> pages = new PageImpl<RouterGroupDto>(collect,pageable,collect.size());
//		
//		return ResponseEntity.ok(pages);
//	}

	
	// Get RouterGroup by id
	@GetMapping("/fetchRouterGroup/{id}")
	public Response<RouterGroup> getRouterGroupById(@PathVariable(value = "id") String routerGroupId)
			throws ResourceNotFoundException {
		return this.routerGroupService.getRouterGroupById(routerGroupId);
	}

	// add RouterGroup into the database
	@PostMapping("/saveRouterGroup")
	public ResponseEntity<Response<?>>createRouterGroup(@RequestBody RouterGroup routerGroup) {
		
		return new ResponseEntity<Response<?>>(routerGroupService.createRouterGroup(routerGroup),HttpStatus.CREATED);
	}

	// for updating the RouterGroup
	@PutMapping("/updateRouterGroup/{id}")
	public ResponseEntity<RouterGroup> updateRouterGroup(@PathVariable(value = "id") String routerGroupId,
			@RequestBody RouterGroup routerGroupDetails) throws ResourceNotFoundException {
		return this.routerGroupService.updateRouterGroup(routerGroupId, routerGroupDetails);
	}

	// Delete RouterGroup from database
	@DeleteMapping("/deleteRouterGroup/{id}")
	public String deleteRouterGroup(@PathVariable(value = "id") String routerGroupId)
			throws ResourceNotFoundException {
		return this.routerGroupService.deleteRouterGroup(routerGroupId);
	}

}
