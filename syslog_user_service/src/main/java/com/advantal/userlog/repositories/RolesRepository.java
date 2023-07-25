package com.advantal.userlog.repositories;

import com.advantal.userlog.model.Roles;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends MongoRepository<Roles, String> {
	Roles findByRoleName(String name);
}