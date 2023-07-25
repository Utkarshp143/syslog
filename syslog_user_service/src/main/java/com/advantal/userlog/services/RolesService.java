package com.advantal.userlog.services;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.advantal.userlog.CustomException.ResourceNotFoundException;
import com.advantal.userlog.dto.Response;
import com.advantal.userlog.model.Roles;

public interface RolesService {

	Page<Roles> getAllRoles(Pageable pageable, String searchTerm, String order, String field);


	public Response<Roles> getRoleById(String roleId) throws ResourceNotFoundException;

	Response createRole(Roles role);

	ResponseEntity<Roles> updateRole(String roleId, Roles roleDetails) throws ResourceNotFoundException;

	String deleteRole(String roleId) throws ResourceNotFoundException;

}