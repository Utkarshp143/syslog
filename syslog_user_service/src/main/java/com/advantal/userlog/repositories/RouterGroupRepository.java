package com.advantal.userlog.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.advantal.userlog.model.RouterGroup;

@Repository
public interface RouterGroupRepository extends MongoRepository<RouterGroup, String> {
	RouterGroup findByRouterGroupName(String name);
}
