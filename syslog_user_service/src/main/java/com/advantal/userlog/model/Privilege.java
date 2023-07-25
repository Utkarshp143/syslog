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
@Document(collection = "Privilege")

public class Privilege{

	@Id
	private String privilegeId;

	private String privilegeName;

	private Date createdDate;

	private Date updatedDate;
	
	private boolean active;
}

