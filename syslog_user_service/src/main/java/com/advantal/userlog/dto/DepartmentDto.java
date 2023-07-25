package com.advantal.userlog.dto;


import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DepartmentDto {
	
	private String departmentId;

	private String departmentName;

	private Date createdDate;

	private Date updatedDate;
}
