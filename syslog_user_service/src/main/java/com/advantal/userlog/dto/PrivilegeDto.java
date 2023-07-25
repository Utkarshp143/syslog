package com.advantal.userlog.dto;

import java.util.Date;

import lombok.Data;


@Data
public class PrivilegeDto{

	private String privilegeId;

	private String privilegeName;

	private Date createdDate;

	private Date updatedDate;
	
	private boolean active;
}