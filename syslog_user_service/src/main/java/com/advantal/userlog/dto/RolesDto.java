package com.advantal.userlog.dto;


import lombok.Data;

import java.util.Date;


@Data
public class RolesDto{

	private String roleId;

	private String roleName;
	
	private Date createdDate;

	private Date updatedDate;
	
	//private Module[] module;
	
	private boolean active;

}