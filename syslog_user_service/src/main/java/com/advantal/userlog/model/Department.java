package com.advantal.userlog.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "Department")

public class Department {
	
	@Id
	private String departmentId;

	private String departmentName;

	private Date createdDate;

	private Date updatedDate;
}
