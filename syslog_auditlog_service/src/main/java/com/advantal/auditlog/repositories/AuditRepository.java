package com.advantal.auditlog.repositories;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.advantal.auditlog.model.Audit;

@Repository
public interface AuditRepository extends MongoRepository<Audit, String>{

}
