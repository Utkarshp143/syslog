package com.advantal.userlog.dto;

import java.util.Date;


import com.advantal.userlog.model.RouterGroup;

import lombok.Data;


@Data
public class RouterDto 
{
	
	private String id;
	
	private String IP;
	
	private String host;
	
	//private LocationDto locationDto;
	
	//private RouterGroup routerGroup;
	
	private Date lastMessageSent;
}