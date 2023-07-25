package com.advantal.userlog.dto;
import java.util.Date;

import com.advantal.userlog.model.Privilege;

import lombok.Data;


@Data
public class ModuleDto{

	private String moduleId;

	private String moduleName;

	private Date createdDate;

	private Date updatedDate;
	
	//private Privilege[] privilege;
	
	private boolean active;

}
