package com.advantal.userlog.services;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.advantal.userlog.CustomException.ResourceNotFoundException;
import com.advantal.userlog.dto.Response;
import com.advantal.userlog.model.Privilege;

public interface PrivilegeService {

	Page<Privilege> getAllPrivileges(Pageable pageable, String searchTerm, String order, String field);

	public Response<Privilege> getPrivilegeById(String privilegeId) throws ResourceNotFoundException;

	Response createPrivilege(Privilege privilege);

	ResponseEntity<Privilege> updatePrivilege(String privilegeId, Privilege privilegeDetails)
			throws ResourceNotFoundException;

	String deletePrivilege(String privilegeId) throws ResourceNotFoundException;

}
