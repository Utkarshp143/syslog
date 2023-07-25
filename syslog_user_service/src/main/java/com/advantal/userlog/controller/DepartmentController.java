package com.advantal.userlog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.advantal.userlog.CustomException.ResourceNotFoundException;
import com.advantal.userlog.dto.DepartmentDto;
import com.advantal.userlog.dto.Response;
import com.advantal.userlog.model.Department;
import com.advantal.userlog.serviceImpl.DepartmentServiceImpl;

@RestController
@RequestMapping("/departmentApi")

public class DepartmentController {

	@Autowired
	private DepartmentServiceImpl departmentService;
	// WITH MODEL
	// Get List of the Departments
	@GetMapping("/getAllDepartments")
	public ResponseEntity<Page<Department>> getAllDepartmentModel(
			@RequestParam(required = false) String searchTerm,
			@RequestParam(required = false) String order, 
			@RequestParam(required = false) String field,
			Pageable pageable) {
		Page<Department> departments = departmentService.getAllDepartments(pageable, searchTerm, order, field);
		return ResponseEntity.ok(departments);
	}
	
	// Get Department by ID
	@GetMapping("/fetchDepartment/{id}")
	public Response<Department> getDepartmentById(@PathVariable(value = "id") String departmentId)
			throws ResourceNotFoundException {
		return this.departmentService.getDepartmentById(departmentId);
	}
	
	// add Department into the database
	@PostMapping("/saveDepartment")
	public ResponseEntity<Response>createDepartment(@RequestBody DepartmentDto department) {
		return new ResponseEntity<Response>(departmentService.createDepartment(department),HttpStatus.CREATED);
	}

	// for updating the Department
	@PutMapping("/updateDepartment/{id}")
	public ResponseEntity<Department> updateDepartment(@PathVariable(value = "id") String departmentId,
			@RequestBody Department departmentDetails) throws ResourceNotFoundException {
		return this.departmentService.updateDepartment(departmentId, departmentDetails);
	}

	// Delete Department from database
	@DeleteMapping("/deleteDepartment/{id}")
	public String deleteDepartment(@PathVariable(value = "id") String departmentId)
			throws ResourceNotFoundException {
		return this.departmentService.deleteDepartment(departmentId);
	}

}