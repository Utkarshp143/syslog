package com.advantal.userlog.model;

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
@Document(collection = "RouterGroup")

public class RouterGroup 
{
	@Id
	private String routerGroupID;
	
	private String routerGroupName;

	private boolean active;
}