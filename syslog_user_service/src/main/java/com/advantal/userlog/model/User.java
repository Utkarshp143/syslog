package com.advantal.userlog.model;

import lombok.*;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "User")

public class User {

    @Id
    private String userId;
    
    private String userName;
    
    @JsonIgnore
    private String password;
    
    private long contactNumber;
    
    private double employeeCode;
    
    @DBRef
    private Department department;
        
    private String additionalInfo;
    
    private Date createdDate;
    
    @DBRef
    private Roles role;
    
	private boolean active;

}