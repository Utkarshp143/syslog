package com.advantal.auditlog.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import com.advantal.auditlog.CustomException.ResourceNotFoundException;
import com.advantal.auditlog.model.Audit;
import com.advantal.auditlog.serviceImpl.AuditServiceImpl;

@RestController
@RequestMapping("/auditApi")
public class AuditController 
{

	@Autowired
	private AuditServiceImpl auditService;

	
	// Get List of the Audits
	@GetMapping("/getAllAudits")
	public ResponseEntity<Page<Audit>> getAllAudits(
			@RequestParam(required = false) String searchTerm,
			@RequestParam(required = false) String order,
			@RequestParam(required = false) String field,
			Pageable pageable) {
		Page<Audit> audits = auditService.getAllAudits(pageable, searchTerm, order, field);
		return ResponseEntity.ok(audits);
	}
	
	
	// Get Audit by ID
	@GetMapping("/fetchAudit/{id}")
	public ResponseEntity<Audit> getAuditById(@PathVariable(value = "id") String auditId)
			throws ResourceNotFoundException {
		return this.auditService.getAuditById(auditId);
	}

	// add Audit into the database
	@PostMapping("/saveAudit")
	public Audit createAudit(@RequestBody Audit audit) {
		return auditService.createAudit(audit);
	}

	// for updating the Audit
	@PutMapping("/updateAudit/{id}")

	public ResponseEntity<Audit> updateAudit(@PathVariable(value = "id") String auditId,
			@RequestBody Audit auditDetails) throws ResourceNotFoundException {
		return this.auditService.updateAudit(auditId, auditDetails);
	}

	// Delete Audit from database
	@DeleteMapping("/deleteAudit/{id}")

	public Map<String, Boolean> deleteAudit(@PathVariable(value = "id") String auditId)
			throws ResourceNotFoundException {
		return this.auditService.deleteAudit(auditId);
	}
	
}