package com.cisco.epx.api.model;

import org.springframework.data.annotation.Id;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors
public class EnrolledCourse {
	
	@Id	
	private String id;
	private String courseId;	
}
