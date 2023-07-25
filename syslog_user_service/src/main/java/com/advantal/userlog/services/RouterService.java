package com.advantal.userlog.services;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.advantal.userlog.CustomException.ResourceNotFoundException;
import com.advantal.userlog.dto.Response;
import com.advantal.userlog.model.Router;

public interface RouterService {

	Page<Router> getAllRouters(Pageable pageable, String searchTerm, String order, String field);

	public Response<Router> getRouterById(String routerId) throws ResourceNotFoundException;

	Response createRouter(Router router);

	ResponseEntity<Router> updateRouter(String routerId, Router routerDetails) throws ResourceNotFoundException;

	String deleteRouter(String routerId) throws ResourceNotFoundException;

}
