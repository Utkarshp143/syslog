package com.advantal.userlog.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.advantal.userlog.model.Router;

@Repository
public interface RouterRepository extends MongoRepository<Router, String>{
	Router findByIP(String ip);
}
