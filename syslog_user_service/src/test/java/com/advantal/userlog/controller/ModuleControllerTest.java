package com.advantal.userlog.controller;

import com.advantal.userlog.CustomException.ResourceNotFoundException;
import com.advantal.userlog.dto.ModuleDto;
import com.advantal.userlog.model.Module;
import com.advantal.userlog.serviceImpl.ModuleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import org.mockito.MockitoAnnotations;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ModuleControllerTest {

    @Mock
    private ModuleServiceImpl moduleService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ModuleController moduleController;

    @BeforeEach
    void setup() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this); // Initialize the mocks

    }

    @Test
    @DisplayName("Get All Modules")

    void getAllModules() {
        // Arrange
        Pageable pageable = mock(Pageable.class);
        Page<Module> modulePage = createMockModulePage();
        when(moduleService.getAllModules(pageable, null, null, null)).thenReturn(modulePage);

        List<ModuleDto> moduleDtoList = createMockModuleDtoList();
        when(modelMapper.map(any(Module.class), eq(ModuleDto.class))).thenReturn(moduleDtoList.get(0));
        when(modelMapper.map(any(Module.class), eq(ModuleDto.class))).thenReturn(moduleDtoList.get(1));

        // Act
        ResponseEntity<Page<ModuleDto>> responseEntity = moduleController.getAllModules(null, null, null, pageable);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Page<ModuleDto> resultPage = responseEntity.getBody();
        assertEquals(2, resultPage.getTotalElements());
        List<ModuleDto> resultList = resultPage.getContent();
        assertEquals(moduleDtoList.get(0), resultList.get(0));
        assertEquals(moduleDtoList.get(1), resultList.get(1));
        verify(moduleService, times(1)).getAllModules(pageable, null, null, null);
        verify(modelMapper, times(2)).map(any(Module.class), eq(ModuleDto.class));
    }

//    @Test
//    @DisplayName("Get Module By ID")
//
//    void getModuleById() throws ResourceNotFoundException {
//        // Arrange
//        String moduleId = "123";
//        Module module = createMockModule();
//        when(moduleService.getModuleById(moduleId)).thenReturn(ResponseEntity.ok(module));
//
//        // Act
//        ResponseEntity<Module> responseEntity = moduleController.getModuleById(moduleId);
//
//        // Assert
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertEquals(module, responseEntity.getBody());
//        verify(moduleService, times(1)).getModuleById(moduleId);
//    }

    @Test
    @DisplayName("Get Module By Invalid ID")
 
    void getModuleByNonExistingId() throws ResourceNotFoundException {
        // Arrange
        String moduleId = "123";
        when(moduleService.getModuleById(moduleId)).thenThrow(new ResourceNotFoundException("Module not found"));

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            moduleController.getModuleById(moduleId);
        });
        assertEquals("Module not found", exception.getMessage());
        verify(moduleService, times(1)).getModuleById(moduleId);
    }

    // Additional test cases for other controller methods can be added here

    private Page<Module> createMockModulePage() {
        List<Module> moduleList = new ArrayList<>();
        moduleList.add(createMockModule());
        moduleList.add(createMockModule());
        return new PageImpl<>(moduleList);
    }

    private List<ModuleDto> createMockModuleDtoList() {
        List<ModuleDto> moduleDtoList = new ArrayList<>();
        moduleDtoList.add(new ModuleDto());
        moduleDtoList.add(new ModuleDto());
        return moduleDtoList;
    }

    private Module createMockModule() {
        Module module = new Module();
        // Set mock properties
        return module;
    }
}
