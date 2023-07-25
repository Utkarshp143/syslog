package com.advantal.userlog.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "Router")

public class Router 
{
	
	@Id
	private String id;
	
	private String IP;
	
	private String host;
	
	@DBRef
	private Location location;
	
	@DBRef
	private RouterGroup routerGroup;
	
	private Date lastMessageSent;
}