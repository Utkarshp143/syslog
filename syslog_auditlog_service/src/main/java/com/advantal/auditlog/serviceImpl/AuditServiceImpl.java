package com.advantal.auditlog.serviceImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.util.StringUtils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import com.advantal.auditlog.CustomException.ResourceNotFoundException;
import com.advantal.auditlog.model.Audit;
import com.advantal.auditlog.repositories.AuditRepository;
import com.advantal.auditlog.services.AuditService;

@Service
public class AuditServiceImpl implements AuditService {

	@Autowired
	private AuditRepository auditRepository;

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public Page<Audit> getAllAudits(Pageable pageable, String searchTerm, String order,
			String field) {
		// TODO Auto-generated method stub
        Criteria criteria = new Criteria();
        if (StringUtils.hasText(searchTerm)) {
            criteria.orOperator(
    	            Criteria.where("description").regex(".*" + searchTerm + ".*", "i"),
    	            Criteria.where("userId").regex(".*" + searchTerm + ".*", "i"),
    	            Criteria.where("moduleId").regex(".*" + searchTerm + ".*", "i"),
    	            Criteria.where("roleId").regex(".*" + searchTerm + ".*", "i"),
    	            Criteria.where("action").regex(".*" + searchTerm + ".*", "i"),
    	            Criteria.where("createdDate").regex(".*" + searchTerm + ".*", "i")
            );
        }

        Query query = new Query(criteria);
        if (StringUtils.hasText(field) && StringUtils.hasText(order)) {
            Sort.Direction sortDirection = Sort.Direction.ASC;
            if (order.equalsIgnoreCase("desc")) {
                sortDirection = Sort.Direction.DESC;
            }
            query.with(Sort.by(sortDirection, field));
        }

        query.with(pageable);
        // Execute the query and retrieve the results as a List<User>
        List<Audit> userList = mongoTemplate.find(query, Audit.class);

        // Create a Page<User> using PageableExecutionUtils
        return PageableExecutionUtils.getPage(userList, pageable,
                () -> mongoTemplate.count(query, Audit.class));	
        }	
	

	@Override
	public Optional<Audit> findById(String auditId) {
		// TODO Auto-generated method stub
		return auditRepository.findById(auditId);
	}

	@Override
	public ResponseEntity<Audit> getAuditById(@PathVariable(value = "id") String auditId)
			throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		Audit audit = auditRepository.findById(auditId)
				.orElseThrow(() -> new ResourceNotFoundException("Audit not found for this id : " + auditId));
		return ResponseEntity.ok().body(audit);
	}

	@Override
	public Audit createAudit(Audit audit) {
		return this.auditRepository.save(audit);
		// TODO Auto-generated method stub
	}

	@Override
	public ResponseEntity<Audit> updateAudit(String auditId, Audit auditDetails) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		Audit audit = auditRepository.findById(auditId)
				.orElseThrow(() -> new ResourceNotFoundException("Audit not found for this id : " + auditId));

		audit.setAction(auditDetails.getAction());
		audit.setDescription(auditDetails.getDescription());
		audit.setModuleId(auditDetails.getModuleId());
		audit.setRoleId(auditDetails.getRoleId());
		audit.setUserId(auditDetails.getUserId());
		audit.setUpdatedDate(new Date());
		final Audit updatedAudit = auditRepository.save(audit);

		return ResponseEntity.ok(updatedAudit);
	}

	@Override
	public Map<String, Boolean> deleteAudit(String auditId) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
        Audit audit = auditRepository.findById(auditId)
                .orElseThrow(() -> new ResourceNotFoundException("Audit not found for this id : " + auditId));
        auditRepository.delete(audit);

                Map<String, Boolean> response = new HashMap<>();
                response.put("deleted", Boolean.TRUE);
                return response;
	}

}
