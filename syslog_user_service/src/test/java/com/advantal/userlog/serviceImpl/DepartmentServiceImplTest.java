package com.advantal.userlog.serviceImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.Collections;
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

    private DepartmentDto blankDepartment;

    private DepartmentDto duplicateDepartment;

    @BeforeEach
    void setUp() {
    	department = DepartmentDto.builder()
    			.departmentId("1")
    			.departmentName("Test")
    			.createdDate(new Date())
    			.updatedDate(new Date())
    			.build();
        
    	blankDepartment = DepartmentDto.builder()
    			.departmentId("2")
    			.departmentName("")
    			.createdDate(new Date())
    			.updatedDate(new Date())
    			.build();
        
    	duplicateDepartment = DepartmentDto.builder()
    			.departmentId("3")
    			.departmentName("Test")
    			.createdDate(new Date())
    			.updatedDate(new Date())
    			.build();
        
    	
    }

    @Test
    @DisplayName("Test saving a new department")
    public void saveNewDepartment() {
        Response response = departmentService.createDepartment(department);
        assertEquals("201", response.getStatusCode());
        assertEquals("Department Created Successfully!", response.getMessage());
    }

//    @Test
//    @DisplayName("Test saving a new department")
//    public void saveNewDepartment() {
//        //when(mongoTemplate.find(any(), eq(Department.class))).thenReturn(Collections.emptyList(), Collections.singletonList(new Department()));
//
//        Response response = departmentService.createDepartment(department);
//        assertEquals("201", response.getStatusCode());
//        assertEquals("Department Created Successfully!", response.getMessage());
//    }

    
    @Test
    @DisplayName("Test saving a department with a blank name")
    public void saveDepartmentWithBlankName() {
        Response response = departmentService.createDepartment(blankDepartment);
        assertEquals("400", response.getStatusCode());
        assertEquals("Department Name cannot be blank!", response.getMessage());
    }

//    @Test
//    @DisplayName("Test saving a duplicate department")
//    public void saveDuplicateDepartment() {
//        when(mongoTemplate.find(any(), eq(Department.class))).thenReturn(Collections.singletonList(new Department()));
//
//        Response response = departmentService.createDepartment(duplicateDepartment);
//        assertEquals("409", response.getStatusCode());
//        assertEquals("Department already Exists!", response.getMessage());
//    }
    
    @Test
    @DisplayName("Test saving a duplicate department")
    public void saveDuplicateDepartment() {
        Response response = departmentService.createDepartment(duplicateDepartment);
        assertEquals("409", response.getStatusCode());
        assertEquals("Department already Exists!", response.getMessage());
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
//        // Mock the departmentRepository to return duplicateDepartment when findByDepartmentName is called with anyString()
//        when(departmentRepository.findByDepartmentName(anyString())).thenAnswer(invocation -> {
//            String departmentName = invocation.getArgument(0);
//            if (departmentName.equals(duplicateDepartment.getDepartmentName())) {
//                // If the department name matches the duplicateDepartment name, return the duplicateDepartment
//                return duplicateDepartment;
//            } else {
//                // If the department name doesn't match, return null (indicating department doesn't exist)
//                return null;
//            }
//        });
//
//        // Call the createDepartment method with the original department object
//        Response response = departmentService.createDepartment(department);
//
//        // Assertions
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
