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
import com.advantal.userlog.dto.RouterDto;
import com.advantal.userlog.dto.UserDto;
import com.advantal.userlog.model.Router;
import com.advantal.userlog.serviceImpl.RouterServiceImpl;


@RestController
@RequestMapping("/routerApi")
public class RouterController {


	@Autowired
	private RouterServiceImpl routerService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	// WITH MODEL
	// Get List of all Routers
	@GetMapping("/getAllRouterModels")
	public ResponseEntity<Page<Router>> getAllRouterModels(
			@RequestParam(required = false) String searchTerm, 
			@RequestParam(required = false) String order,
			@RequestParam(required = false) String field, 
			Pageable pageable) {
		
		Page<Router> routers = routerService.getAllRouters(pageable, searchTerm, order, field);
		
		return ResponseEntity.ok(routers);
	}

		// Get List of all Routers DTO
		@GetMapping("/getAllRouters")
		public ResponseEntity<Page<RouterDto>> getAllRouters(
				@RequestParam(required = false) String searchTerm, 
				@RequestParam(required = false) String order,
				@RequestParam(required = false) String field, 
				Pageable pageable) {
			
			Page<Router> routers = routerService.getAllRouters(pageable, searchTerm, order, field);
			List<RouterDto> collect = routers.stream().map(mod->modelMapper.map(mod,RouterDto.class)).collect(Collectors.toList());
			Page<RouterDto> pages = new PageImpl<RouterDto>(collect,pageable,collect.size());
			return ResponseEntity.ok(pages);
		}
	
	// Get Router by id
	@GetMapping("/fetchRouter/{id}")
	public Response<Router> getRouterById(@PathVariable(value = "id") String routerId)
			throws ResourceNotFoundException {
		return this.routerService.getRouterById(routerId);
	}

	// add Router into the database
	@PostMapping("/saveRouter")
	public ResponseEntity<Response>createRouter(@RequestBody Router router) {
		return new ResponseEntity<Response>(routerService.createRouter(router),HttpStatus.CREATED);
	}

	// for updating the Router
	@PutMapping("/updateRouter/{id}")
	public ResponseEntity<Router> updateRouter(@PathVariable(value = "id") String routerId,
			@RequestBody Router routerDetails) throws ResourceNotFoundException {
		return this.routerService.updateRouter(routerId, routerDetails);
	}

	// Delete Router from database
	@DeleteMapping("/deleteRouter/{id}")
	public String deleteRouter(@PathVariable(value = "id") String routerId)
			throws ResourceNotFoundException {
		return this.routerService.deleteRouter(routerId);
	}

}
