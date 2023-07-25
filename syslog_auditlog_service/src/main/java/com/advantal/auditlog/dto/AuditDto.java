package com.advantal.auditlog.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditDto {
    private String id;
    private String description;
    private String action;
    private String userId;
    private String moduleId;
    private String roleId;
    private Date createdDate;
    private Date updatedDate;

}
