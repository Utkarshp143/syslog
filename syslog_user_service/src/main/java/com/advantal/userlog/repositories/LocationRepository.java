package com.advantal.userlog.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.advantal.userlog.model.Location;

@Repository
public interface LocationRepository extends MongoRepository<Location, String>{
	Location findByLocationName(String name);
}
