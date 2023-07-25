package com.advantal.userlog.model;

import lombok.*;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "Roles")

public class Roles{

	@Id
	private String roleId;

	private String roleName;
	
	private Date createdDate;

	private Date updatedDate;
	
	@DBRef
	private Module[] module;
	
	private boolean active;

}