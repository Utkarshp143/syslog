package com.advantal.auditlog.model;

import lombok.*;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Document(collection = "audit")
public class Audit {

	@Id
	private String id;

	private String description;

	private String action;

	private String userId;

	private String moduleId;

	private String roleId;

	private Date createdDate;

	private Date updatedDate;

}
