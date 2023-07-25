package com.advantal.userlog.services;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.advantal.userlog.CustomException.ResourceNotFoundException;
import com.advantal.userlog.dto.Response;
import com.advantal.userlog.model.Module;

public interface ModuleService {

	Page<Module> getAllModules(Pageable pageable, String searchTerm, String order, String field);

	public Response<Module> getModuleById(String moduleId) throws ResourceNotFoundException;

	Response createModule(Module module);

	ResponseEntity<Module> updateModule(String moduleId, Module moduleDetails) throws ResourceNotFoundException;

	String deleteModule(String moduleId) throws ResourceNotFoundException;

}