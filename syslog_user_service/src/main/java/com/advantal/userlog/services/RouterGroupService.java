package com.advantal.userlog.services;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.advantal.userlog.CustomException.ResourceNotFoundException;
import com.advantal.userlog.dto.Response;
import com.advantal.userlog.model.RouterGroup;

public interface RouterGroupService {

	Page<RouterGroup> getAllRouterGroups(Pageable pageable, String searchTerm, String order, String field);

	public Response<RouterGroup> getRouterGroupById(String routerGroupId) throws ResourceNotFoundException;

	Response createRouterGroup(RouterGroup routerGroup);

	ResponseEntity<RouterGroup> updateRouterGroup(String routerGroupId, RouterGroup routerGroupDetails)
			throws ResourceNotFoundException;

	String deleteRouterGroup(String routerGroupId) throws ResourceNotFoundException;

}
