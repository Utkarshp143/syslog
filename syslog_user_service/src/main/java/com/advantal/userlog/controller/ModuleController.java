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
import com.advantal.userlog.dto.ModuleDto;
import com.advantal.userlog.dto.Response;
import com.advantal.userlog.model.Module;
import com.advantal.userlog.serviceImpl.ModuleServiceImpl;




@RestController
@RequestMapping("/moduleApi")
public class ModuleController {


	@Autowired
	private ModuleServiceImpl moduleService;
	
	@Autowired
	private ModelMapper modelMapper;
	
		// with Model
		@GetMapping("/getAllModuleModel")
		public ResponseEntity<Page<Module>> getAllModuleModel(
				@RequestParam(required = false) String searchTerm,
				@RequestParam(required = false) String order,
				@RequestParam(required = false) String field,
				Pageable pageable) {
			
			
			Page<Module> modules = moduleService.getAllModules(pageable, searchTerm, order, field);
			return ResponseEntity.ok(modules);
		}
		
		// with DTO
		// Get List of all Modules
		@GetMapping("/getAllModules")
		@SuppressWarnings("all")
		public ResponseEntity<Page<ModuleDto>> getAllModules(
				@RequestParam(required = false) String searchTerm, 
				@RequestParam(required = false) String order,
				@RequestParam(required = false) String field, 
				Pageable pageable) {
			
			Page<Module> modules = moduleService.getAllModules(pageable, searchTerm, order, field);
			List<ModuleDto> collect =  modules.stream().map(mod->modelMapper.map(mod ,ModuleDto.class)).collect(Collectors.toList());
		    Page<ModuleDto> pages = new PageImpl<ModuleDto>(collect, pageable, collect.size());

			return ResponseEntity.ok(pages);
		}
	// Get Module by id
	@GetMapping("/fetchModule/{id}")
	public Response<Module> getModuleById(@PathVariable(value = "id") String moduleId)
			throws ResourceNotFoundException {
		return this.moduleService.getModuleById(moduleId);
	}

	// add Module into the database
	@PostMapping("/saveModule")
	public ResponseEntity<Response>createModule(@RequestBody Module module) {
		return new ResponseEntity<Response>(moduleService.createModule(module),HttpStatus.CREATED);
	}


	// for updating the Module
	@PutMapping("/updateModule/{id}")
	public ResponseEntity<Module> updateModule(@PathVariable(value = "id") String moduleId,
			@RequestBody Module moduleDetails) throws ResourceNotFoundException {
		return this.moduleService.updateModule(moduleId, moduleDetails);
	}

	// Delete Module from database
	@DeleteMapping("/deleteModule/{id}")
	public String deleteModule(@PathVariable(value = "id") String moduleId)
			throws ResourceNotFoundException {
		return this.moduleService.deleteModule(moduleId);
	}

}
