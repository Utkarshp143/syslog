package com.advantal.userlog.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LocationDto
{
	private String locationID;
	
	private String locationName;
}
