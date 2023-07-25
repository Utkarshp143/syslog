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
import com.advantal.userlog.dto.LocationDto;
import com.advantal.userlog.dto.Response;
import com.advantal.userlog.model.Location;
import com.advantal.userlog.serviceImpl.LocationServiceImpl;


@RestController
@RequestMapping("/locationApi")
public class LocationController {

	@Autowired
	private LocationServiceImpl locationService;
	
	
	// With Model
	// Get List of the Locations
	@GetMapping("/getAllLocations")
	public ResponseEntity<Page<Location>> getAllLocationModel(
			@RequestParam(required = false) String searchTerm,
			@RequestParam(required = false) String order,
			@RequestParam(required = false) String field,
			Pageable pageable) {
		
		
		Page<Location> users = locationService.getAllLocations(pageable, searchTerm, order, field);
		return ResponseEntity.ok(users);
	}
	
//	// With DTO
//	// Get List of the Locations= DTO
//	@GetMapping("/getAllLocations")
//	public ResponseEntity<Page<LocationDto>> getAllLocations(
//			@RequestParam(required = false) String searchTerm,
//			@RequestParam(required = false) String order,
//			@RequestParam(required = false) String field,
//			Pageable pageable) {
//		
//		Page<Location> locations = locationService.getAllLocations(pageable, searchTerm, order, field);
//		List<LocationDto> collect = locations.stream().map(mod-> modelMapper.map(mod, LocationDto.class)).collect(Collectors.toList());
//		Page<LocationDto> pages = new PageImpl<LocationDto>(collect,pageable,collect.size());
//		return ResponseEntity.ok(pages);
//	}

	
	// Get Location by id
	@GetMapping("/fetchLocation/{id}")
	public Response<Location> getLocationById(@PathVariable(value = "id") String locationId)
			throws ResourceNotFoundException {
		return this.locationService.getLocationById(locationId);
	}

	// add Location into the database
	@PostMapping("/saveLocation")
	public ResponseEntity<Response>createLocation(@RequestBody LocationDto location) {
		return new ResponseEntity<Response>(locationService.createLocation(location),HttpStatus.CREATED);
	}
	
	// for updating the Location
	@PutMapping("/updateLocation/{id}")
	public ResponseEntity<Location> updateLocation(@PathVariable(value = "id") String locationId,
			@RequestBody Location locationDetails) throws ResourceNotFoundException {
		return this.locationService.updateLocation(locationId, locationDetails);
	}

	// Delete Location from database
	@DeleteMapping("/deleteLocation/{id}")
	public String deleteLocation(@PathVariable(value = "id") String locationId)
			throws ResourceNotFoundException {
		return this.locationService.deleteLocation(locationId);
	}

}
