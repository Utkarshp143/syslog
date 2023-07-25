package com.advantal.userlog.repositories;

import com.advantal.userlog.model.User;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

	User findByUserName(String name);
	
	List<User> findAllByActive(boolean val);

	Page<User> findAllByActive(boolean val, Pageable pageable);
}