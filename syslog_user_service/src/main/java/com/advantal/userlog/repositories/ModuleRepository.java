package com.advantal.userlog.repositories;

import com.advantal.userlog.model.Module;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleRepository extends MongoRepository<Module, String> {
	Module findByModuleName(String name);
}