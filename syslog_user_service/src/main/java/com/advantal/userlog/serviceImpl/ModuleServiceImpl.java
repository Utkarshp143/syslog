package com.advantal.userlog.serviceImpl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.advantal.userlog.CustomException.ResourceNotFoundException;
import com.advantal.userlog.dto.Response;
import com.advantal.userlog.repositories.ModuleRepository;
import com.advantal.userlog.services.ModuleService;
import com.advantal.userlog.model.Module;
import com.advantal.userlog.model.Privilege;
import com.advantal.userlog.model.Router;

@Service
public class ModuleServiceImpl implements ModuleService{
	
	@Autowired
    private ModuleRepository moduleRepository;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	private final static Logger LOGGER = LoggerFactory.getLogger(ModuleServiceImpl.class);
	
	List<String> filterArray = Arrays.asList("moduleId","moduleName","privilege","createdDate","updatedDate");


	@Override
	public Page<Module> getAllModules(Pageable pageable, String searchTerm, String order,
			String filter) {
		// TODO Auto-generated method stub
        Criteria criteria = new Criteria();
        if (StringUtils.hasText(searchTerm)) {
            criteria.orOperator(
            		

                    Criteria.where("moduleId").regex(".*" + searchTerm + ".*", "i"),
                    Criteria.where("moduleName").regex(".*" + searchTerm + ".*", "i"),
                    Criteria.where("privilege").regex(".*" + searchTerm + ".*", "i"),
                    Criteria.where("createdDate").regex(".*" + searchTerm + ".*", "i"),
                    Criteria.where("updatedDate").regex(".*" + searchTerm + ".*", "i")
            );
        }

        Query query = new Query(criteria);

	    if (StringUtils.hasText(order)) {
	        if (order.equalsIgnoreCase("asc")) {
	            query.with(Sort.by(Sort.Direction.ASC, StringUtils.hasText(filter) && filterArray.contains(filter) ? filter : "moduleName"));
	        } else if (order.equalsIgnoreCase("desc")) {
	            query.with(Sort.by(Sort.Direction.DESC, StringUtils.hasText(filter) && filterArray.contains(filter) ? filter : "moduleName"));
	        } else {
	            throw new IllegalArgumentException("Invalid value for 'order' parameter. It should be 'asc' or 'desc'.");
	        }
	    } else {
	        // If order is not provided, set the default sort order to ASCENDING (ascending)
	        query.with(Sort.by(Sort.Direction.ASC, StringUtils.hasText(filter) && filterArray.contains(filter) ? filter : "moduleName"));
	    }

	    query.with(pageable);
	    List<Module> moduleList = mongoTemplate.find(query, Module.class);

	    return PageableExecutionUtils.getPage(moduleList, pageable,
	            () -> mongoTemplate.count(query, Module.class));
	}


    public Response<Module> getModuleById(@PathVariable(value = "id") String moduleId)
            throws ResourceNotFoundException {
    	Response response = new Response();
    	if(moduleRepository.findById(moduleId) != null) {
    		response.setStatusCode("201");
    		response.setMessage("Module found!");
    		response.setData(moduleRepository.findById(moduleId));
    	}
    	else {
    		response.setStatusCode("409");
    		response.setMessage("Module doesn't exist!");
    	}
    	return response;
    }


    public Optional<Module> findById(String moduleId) {
        Optional<Module> module = moduleRepository.findById(moduleId);
        return  module;

    }

    public Response createModule(Module module) {
    	Response response = new Response();
    	
    	
    	try {
    		Module findByModuleName = this.moduleRepository.findByModuleName(module.getModuleName().toLowerCase().trim());
    		if(findByModuleName == null && module.getPrivilege() != null) {
    			module.setModuleName(module.getModuleName().toLowerCase().trim());
    			module.setActive(true);
    			module.setCreatedDate(new Date());
    			module.setUpdatedDate(new Date());
    			this.moduleRepository.save(module);
    			response.setStatusCode("201");
    			response.setMessage("Module Created Successfully!");
    			
    		}
    		else if(module.getPrivilege() == null && findByModuleName == null) {
    			response.setStatusCode("409");
    			response.setMessage("Can't create Module as no Privilege is selected!");
    		}
    		
    		else {
    			response.setMessage("Module already exists!");
    			response.setStatusCode("409");
    		}
    	}catch(Exception e) {
			 LOGGER.info(e.getMessage());
			 response.setMessage("Exception Occurred!");
    	}
    	return response;
    }

    public ResponseEntity<Module> updateModule(@PathVariable(value = "id") String moduleId,
                                                   @RequestBody Module moduleDetails) throws ResourceNotFoundException {

    	Module  module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new ResourceNotFoundException("Module not found for this id :: " + moduleId));
    	
    	module.setModuleName(moduleDetails.getModuleName());
    	module.setUpdatedDate(new Date());
        final Module updatedModule = moduleRepository.save(module);

        return ResponseEntity.ok(updatedModule);
    }


    public String deleteModule(@PathVariable(value = "id") String moduleId)
            throws ResourceNotFoundException {
    	Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new ResourceNotFoundException("Module not found for this id :: " + moduleId));
    	moduleRepository.delete(module);
    		return "Module deleted Successfully!";
                
           }

}
