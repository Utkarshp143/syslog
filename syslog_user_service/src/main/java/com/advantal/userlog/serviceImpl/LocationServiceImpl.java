package com.advantal.userlog.serviceImpl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.advantal.userlog.CustomException.ResourceNotFoundException;
import com.advantal.userlog.dto.LocationDto;
import com.advantal.userlog.dto.Response;
import com.advantal.userlog.model.Department;
import com.advantal.userlog.model.Location;
import com.advantal.userlog.repositories.LocationRepository;
import com.advantal.userlog.services.LocationService;

@Service
public class LocationServiceImpl implements LocationService
{
	@Autowired
	private LocationRepository locationRepository;
	
	@Autowired
	private MongoTemplate mongoTemplate;

	private final static Logger LOGGER = LoggerFactory.getLogger(LocationServiceImpl.class);

	
	List<String> filterArray = Arrays.asList("locationId","locationName");

	
	@Override
	public Page<Location> getAllLocations(Pageable pageable, String searchTerm, String order,
			String filter) {
		// TODO Auto-generated method stub
        Criteria criteria = new Criteria();
        if (StringUtils.hasText(searchTerm)) {
            criteria.orOperator(
            		
                    Criteria.where("locationId").regex(".*" + searchTerm + ".*", "i"),
                    Criteria.where("locationName").regex(".*" + searchTerm + ".*", "i")
            );
        }

	    Query query = new Query(criteria);

	    if (StringUtils.hasText(order)) {
	        if (order.equalsIgnoreCase("asc")) {
	            query.with(Sort.by(Sort.Direction.ASC, StringUtils.hasText(filter) && filterArray.contains(filter) ? filter : "locationName"));
	        } else if (order.equalsIgnoreCase("desc")) {
	            query.with(Sort.by(Sort.Direction.DESC, StringUtils.hasText(filter) && filterArray.contains(filter) ? filter : "locationName"));
	        } else {
	            throw new IllegalArgumentException("Invalid value for 'order' parameter. It should be 'asc' or 'desc'.");
	        }
	    } else {
	        // If order is not provided, set the default sort order to ASCENDING (ascending)
	        query.with(Sort.by(Sort.Direction.ASC, StringUtils.hasText(filter) && filterArray.contains(filter) ? filter : "locationName"));
	    }

	    query.with(pageable);
	    List<Location> locationList = mongoTemplate.find(query, Location.class);

	    return PageableExecutionUtils.getPage(locationList, pageable,
	            () -> mongoTemplate.count(query, Location.class));	
	}

	@Override
	public Response<Location> getLocationById(String locationId) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		Response response = new Response();
		if(locationRepository.findById(locationId) != null) {
			response.setStatusCode("201");
			response.setMessage("Location found!");
			response.setData(locationRepository.findById(locationId));
		}else {
			response.setStatusCode("409");
			response.setMessage("Location doesn't exist!");
		}
		return response;

	}

	@Override
	public String deleteLocation(String locationId) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		Location location = locationRepository.findById(locationId)
	            .orElseThrow(() -> new ResourceNotFoundException("Location not found for this id : " + locationId));
		locationRepository.delete(location);

		return "Location deleted Successfully!";

	}

	@Override
	public Response createLocation(LocationDto location) {
		// TODO Auto-generated method stub
		Response response = new Response();
		try {
			Location findByLocationName = locationRepository.findByLocationName(location.getLocationName().toLowerCase().trim());
			if(findByLocationName == null)
			{
				Location locat = Location.builder()
						.locationName(location.getLocationName().toLowerCase().trim())
						.build();
				this.locationRepository.save(locat);
				response.setStatusCode("201");
				response.setMessage("Location Created Successfully!");
			}else {
				response.setStatusCode("409");
				response.setMessage("Location already Exists!");
			}
		}catch(Exception e) {
			 LOGGER.info(e.getMessage());
			 response.setMessage("Exception Occurred!");
		}
		return response;
	}

	@Override
	public ResponseEntity<Location> updateLocation(String locationId, Location locationDetails)
			throws ResourceNotFoundException {
		// TODO Auto-generated method stub

		Location location  = locationRepository.findById(locationId)
	            .orElseThrow(() -> new ResourceNotFoundException("Location not found for this id : " + locationId));


		location.setLocationName(locationDetails.getLocationName());
        final Location updatedLocation= locationRepository.save(location);

        return ResponseEntity.ok(updatedLocation);
	}

	public Optional<Location> findById(String locationId) {
		// TODO Auto-generated method stub
        Optional<Location> location = locationRepository.findById(locationId);
        return  location;
	}

}