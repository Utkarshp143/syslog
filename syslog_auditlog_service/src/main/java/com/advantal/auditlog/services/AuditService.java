package com.advantal.auditlog.services;

import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.advantal.auditlog.CustomException.ResourceNotFoundException;
import com.advantal.auditlog.model.Audit;

public interface AuditService {

	Page<Audit> getAllAudits(Pageable pageable, String searchTerm, String order, String field);

	Optional<Audit> findById(String auditId);

	public ResponseEntity<Audit> getAuditById(String auditId) throws ResourceNotFoundException;

	Audit createAudit(Audit audit);

	ResponseEntity<Audit> updateAudit(String auditId, Audit auditDetails) throws ResourceNotFoundException;

	Map<String, Boolean> deleteAudit(String auditId) throws ResourceNotFoundException;
}