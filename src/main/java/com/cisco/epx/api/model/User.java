package com.cisco.epx.api.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class User {
	@Id
	String id;	
	@Indexed(unique = true)
	private String email;
	private String password;
	@Indexed(unique = true)
	private String username;
	private List<String> favorites;
	private Provider provider;
	private List<EnrolledCourse> enrolledCourses;
}
