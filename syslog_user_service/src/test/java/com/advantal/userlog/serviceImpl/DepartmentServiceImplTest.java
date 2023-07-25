package com.advantal.userlog.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;

import com.advantal.userlog.CustomException.ResourceNotFoundException;
import com.advantal.userlog.dto.DepartmentDto;
import com.advantal.userlog.dto.Response;
import com.advantal.userlog.model.Department;
import com.advantal.userlog.repositories.DepartmentRepository;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceImplTest {

    @Mock
    private DepartmentRepository departmentRepository;
    
    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private DepartmentServiceImpl departmentService;

    private DepartmentDto department;

    @BeforeEach
    void setUp() {
    	department = DepartmentDto.builder()
    			.departmentId("1")
    			.departmentName("Test Department")
    			.createdDate(new Date())
    			.updatedDate(new Date())
    			.build();
        
    }

    // JUnit test for saveDepartment method
    @DisplayName("JUnit test for saveDepartment method")
    @Test
    public void saveDepartment(){
    	if(departmentRepository.findByDepartmentName(department.getDepartmentName()) == null) {
            Response response = departmentService.createDepartment(department);
            assertEquals("201",response.getStatusCode());
            assertEquals("Department Created Successfully!", response.getMessage());
    	}else if(department.getDepartmentName().isEmpty()) {
            Response response = departmentService.createDepartment(department);
            assertEquals("400",response.getStatusCode());
            assertEquals("Department Name cannot be blank!", response.getMessage());
    	}
    	else {
            Response response = departmentService.createDepartment(department);
            assertEquals("409",response.getStatusCode());
            assertEquals("Department already Exists!", response.getMessage()); 
    	}
        
    }
//    
//    
//    @Test
//    void testGetAllDepartments() {
//        List<Department> departments = new ArrayList<>();
//        departments.add(department);
//
//        Pageable pageable = null;
//        String searchTerm = "";
//        String order = "";
//        String field = "";
//
//        when(departmentRepository.findAll()).thenReturn(departments);
//        Page<Department> result = departmentService.getAllDepartments(pageable, searchTerm, order, field);
//
//        assertThat(result).isNotNull();
//        assertThat(result.getContent()).hasSize(1);
//        assertThat(result.getContent().get(0).getDepartmentName()).isEqualTo("Test Department");
//    }
//
//
//    @Test
//    void testCreateDepartment() {
//        when(departmentRepository.findByDepartmentName(anyString())).thenReturn(null);
//        when(departmentRepository.save(any(Department.class))).thenReturn(department);
//
//        Response response = departmentService.createDepartment(department);
//
//        assertThat(response).isNotNull();
//        assertThat(response.getStatusCode()).isEqualTo("201");
//        assertThat(response.getMessage()).isEqualTo("Department Created Successfully!");
//    }
//
//    @Test
//    void testCreateDepartmentAlreadyExists() {
//        when(departmentRepository.findByDepartmentName(anyString())).thenReturn(department);
//
//        Response response = departmentService.createDepartment(department);
//
//        assertThat(response).isNotNull();
//        assertThat(response.getStatusCode()).isEqualTo("409");
//        assertThat(response.getMessage()).isEqualTo("Department already Exists!");
//    }
//
//    @Test
//    void testUpdateDepartment() throws ResourceNotFoundException {
//        when(departmentRepository.findById(anyString())).thenReturn(Optional.of(department));
//        when(departmentRepository.save(any(Department.class))).thenReturn(department);
//
//        Department updatedDepartment = new Department();
//        updatedDepartment.setDepartmentName("Updated Department");
//
//        ResponseEntity<Department> responseEntity = departmentService.updateDepartment(departmentId, updatedDepartment);
//
//        assertThat(responseEntity).isNotNull();
//        assertThat(responseEntity.getBody().getDepartmentName()).isEqualTo("Updated Department");
//    }
//
//    @Test
//    void testUpdateDepartmentNotFound() {
//        when(departmentRepository.findById(anyString())).thenReturn(Optional.empty());
//
//        try {
//            departmentService.updateDepartment(departmentId, new Department());
//        } catch (ResourceNotFoundException e) {
//            assertThat(e.getMessage()).isEqualTo("Department not found for this id : " + departmentId);
//        }
//    }
//
//    @Test
//    void testDeleteDepartment() throws ResourceNotFoundException {
//        when(departmentRepository.findById(anyString())).thenReturn(Optional.of(department));
//
//        String result = departmentService.deleteDepartment(departmentId);
//
//        assertThat(result).isEqualTo("Department deleted Successfully!");
//    }
//
//    @Test
//    void testDeleteDepartmentNotFound() {
//        when(departmentRepository.findById(anyString())).thenReturn(Optional.empty());
//
//        try {
//            departmentService.deleteDepartment(departmentId);
//        } catch (ResourceNotFoundException e) {
//            assertThat(e.getMessage()).isEqualTo("Department not found for this id : " + departmentId);
//        }
//    }
//
//    @Test
//    void testFindById() {
//        when(departmentRepository.findById(anyString())).thenReturn(Optional.of(department));
//
//        Optional<Department> result = departmentService.findById(departmentId);
//
//        assertThat(result).isNotNull();
//        assertThat(result.isPresent()).isTrue();
//        assertThat(result.get().getDepartmentId()).isEqualTo(departmentId);
//    }
//
//    @Test
//    void testFindByIdNotFound() {
//        when(departmentRepository.findById(anyString())).thenReturn(Optional.empty());
//
//        Optional<Department> result = departmentService.findById(departmentId);
//
//        assertThat(result).isNotNull();
//        assertThat(result.isPresent()).isFalse();
//    }
//
//    @Test
//    void testGetDepartmentById() throws ResourceNotFoundException {
//        when(departmentRepository.findById(anyString())).thenReturn(Optional.of(department));
//
//        Response<Department> response = departmentService.getDepartmentById(departmentId);
//
//        assertThat(response).isNotNull();
//        assertThat(response.getStatusCode()).isEqualTo("201");
//        assertThat(response.getMessage()).isEqualTo("Department found!");
//        assertThat(response.getModel()).isNotNull();
//        assertTrue(response.getModel() != null);
//        assertThat(response.getModel().getDepartmentId()).isEqualTo(departmentId);
//    }
//
//    @Test
//    void testGetDepartmentByIdNotFound() throws ResourceNotFoundException {
//        when(departmentRepository.findById(anyString())).thenReturn(Optional.empty());
//        Response<Department> response = departmentService.getDepartmentById(departmentId);
//
//        assertThat(response).isNotNull();
//        assertThat(response.getStatusCode()).isEqualTo("409");
//        assertThat(response.getMessage()).isEqualTo("Department doesn't exist!");
//        assertThat(response.getModel()).isNotNull();
//        assertThat(response.getModel() != null).isFalse();
//    }
}
