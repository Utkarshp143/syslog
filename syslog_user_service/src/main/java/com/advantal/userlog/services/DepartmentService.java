package com.advantal.userlog.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.advantal.userlog.CustomException.ResourceNotFoundException;
import com.advantal.userlog.dto.DepartmentDto;
import com.advantal.userlog.dto.Response;
import com.advantal.userlog.model.Department;

public interface DepartmentService {

	public Page<Department> getAllDepartments(Pageable pageable, String searchTerm, String order, String field);
	
	Optional<Department> findById(String departmentId);

	public Response<Department> getDepartmentById(String departmentId) throws ResourceNotFoundException;

	public Response createDepartment(DepartmentDto department);

	ResponseEntity<Department> updateDepartment(String departmentId, Department departmentDetails)
			throws ResourceNotFoundException;

	String deleteDepartment(String departmentId) throws ResourceNotFoundException;

}
