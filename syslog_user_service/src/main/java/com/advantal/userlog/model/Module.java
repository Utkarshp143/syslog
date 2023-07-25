package com.advantal.userlog.model;


import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "Module")

public class Module{

	@Id
	private String moduleId;

	private String moduleName;

	private Date createdDate;

	private Date updatedDate;
	
	@DBRef
	private Privilege[] privilege;
	
	private boolean active;

}
