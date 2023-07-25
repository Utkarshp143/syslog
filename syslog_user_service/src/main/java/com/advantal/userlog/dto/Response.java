package com.advantal.userlog.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class Response <T> implements Serializable{
	private String statusCode;
	private String message;
	private transient T data;
	
}
