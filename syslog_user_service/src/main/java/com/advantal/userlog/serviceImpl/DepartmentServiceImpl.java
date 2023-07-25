package com.advantal.userlog.serviceImpl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.advantal.userlog.CustomException.ResourceNotFoundException;
import com.advantal.userlog.dto.DepartmentDto;
import com.advantal.userlog.dto.Response;
import com.advantal.userlog.model.Department;
import com.advantal.userlog.model.RouterGroup;
import com.advantal.userlog.repositories.DepartmentRepository;
import com.advantal.userlog.services.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService{
	
	@Autowired
	private DepartmentRepository departmentRepository;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	private final static Logger LOGGER = LoggerFactory.getLogger(DepartmentServiceImpl.class);

	List<String> filterArray = Arrays.asList("departmentId","departmentName","updatedDate","createdDate");

	
	@Override
	public Page<Department> getAllDepartments(Pageable pageable, String searchTerm, String order, String filter) {
	    Criteria criteria = new Criteria();

	    if (StringUtils.hasText(searchTerm)) {
	        criteria.orOperator(
	                Criteria.where("departmentId").regex(".*" + searchTerm + ".*", "i"),
	                Criteria.where("departmentName").regex(".*" + searchTerm + ".*", "i"),
	                Criteria.where("updatedDate").regex(".*" + searchTerm + ".*", "i"),
	                Criteria.where("createdDate").regex(".*" + searchTerm + ".*", "i")
	        );
	    }

	    Query query = new Query(criteria);

	    if (StringUtils.hasText(order)) {
	        if (order.equalsIgnoreCase("asc")) {
	            query.with(Sort.by(Sort.Direction.ASC, StringUtils.hasText(filter) && filterArray.contains(filter) ? filter : "departmentName"));
	        } else if (order.equalsIgnoreCase("desc")) {
	            query.with(Sort.by(Sort.Direction.DESC, StringUtils.hasText(filter) && filterArray.contains(filter) ? filter : "departmentName"));
	        } else {
	            throw new IllegalArgumentException("Invalid value for 'order' parameter. It should be 'asc' or 'desc'.");
	        }
	    } else {
	        // If order is not provided, set the default sort order to ASCENDING (ascending)
	        query.with(Sort.by(Sort.Direction.ASC, StringUtils.hasText(filter) && filterArray.contains(filter) ? filter : "departmentName"));
	    }

	    query.with(pageable);
	    List<Department> departmentList = mongoTemplate.find(query, Department.class);

	    return PageableExecutionUtils.getPage(departmentList, pageable,
	            () -> mongoTemplate.count(query, Department.class));	
	    
	}
	@Override
	public Response createDepartment(DepartmentDto department) {
		Response response = new Response();
		if(department.getDepartmentName().isEmpty()) {
			response.setStatusCode("400");
		    response.setMessage("Department Name cannot be blank!");
		    
		}
		try {
			Department findByDepartmentName = this.departmentRepository.findByDepartmentName(department.getDepartmentName().toLowerCase().trim());
			if(findByDepartmentName == null) {
				Department dept = Department.builder()
						.departmentName(department.getDepartmentName().toLowerCase().trim())
						.createdDate(new Date())
						.updatedDate(new Date())
						.build();

				this.departmentRepository.save(dept);
				response.setStatusCode("201");
				response.setMessage("Department Created Successfully!");
			}else {
				response.setStatusCode("409");
				response.setMessage("Department already Exists!");
			}
		}catch(Exception e) {
			LOGGER.info(e.getMessage());
			response.setMessage("Exception Occurred!");
		}
		return response;
	}

	
	@Override
	public ResponseEntity<Department> updateDepartment(String departmentId, Department departmentDetails)
			throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		
    	Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found for this id : " + departmentId));

    	//role.setRoleId(roleDetails.getRoleId());
    	department.setDepartmentName(departmentDetails.getDepartmentName());
    	//role.setCreatedDate(roleDetails.getCreatedDate());
    	department.setUpdatedDate(new Date());
        final Department updatedDepartment = departmentRepository.save(department);

        return ResponseEntity.ok(updatedDepartment);
	}

	@Override
	public String deleteDepartment(String departmentId) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found for this id : " + departmentId));
        departmentRepository.delete(department);

		return "Department deleted Successfully!";

	}

	@Override
	public Optional<Department> findById(String departmentId) {
		// TODO Auto-generated method stub
		
	        Optional<Department> department = departmentRepository.findById(departmentId);
	        return  department;

	}
	@Override
	public Response<Department> getDepartmentById(String departmentId) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		Response response = new Response();
		if(departmentRepository.findById(departmentId) != null) {
			response.setStatusCode("201");
			response.setMessage("Department found!");
			response.setData(departmentRepository.findById(departmentId));
		}
		else {
			response.setStatusCode("409");
			response.setMessage("Department doesn't exist!");
		}
		return response;
	}
	
	

}
