package com.advantal.userlog.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.advantal.userlog.model.Privilege;

@Repository
public interface PrivilegeRepository extends MongoRepository<Privilege, String>{
	
	Privilege findByPrivilegeName(String name);

}
