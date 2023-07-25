package com.advantal.userlog.services;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import com.advantal.userlog.CustomException.ResourceNotFoundException;
import com.advantal.userlog.dto.LocationDto;
import com.advantal.userlog.dto.Response;
import com.advantal.userlog.model.Location;

public interface LocationService {

	Page<Location> getAllLocations(Pageable pageable, String searchTerm, String order, String field);

	public Response<Location> getLocationById(String locationId) throws ResourceNotFoundException;

	Response createLocation(LocationDto location);

	ResponseEntity<Location> updateLocation(String locationId, Location locationDetails)
			throws ResourceNotFoundException;

	String deleteLocation(String locationId) throws ResourceNotFoundException;

}
