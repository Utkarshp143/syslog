package com.advantal.userlog.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.advantal.userlog.dto.DepartmentDto;
import com.advantal.userlog.model.Department;

@Repository
public interface DepartmentRepository extends MongoRepository<Department, String>{
	Department findByDepartmentName(String name);

}
