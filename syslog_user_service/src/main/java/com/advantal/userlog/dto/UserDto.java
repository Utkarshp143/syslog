package com.advantal.userlog.dto;


import lombok.Data;

import java.util.Date;


@Data
public class UserDto {

    private String userId;
    
    private String userName;
    
    private String password;
    
    private long contactNumber;
    
    private double employeeCode;
    
    private DepartmentDto departmentDto;
    
    private boolean status;
    
    private String additionalInfo;
    
    private Date createdDate;
    
    //private Roles role;
    
	private boolean active;

}