package com.ran.epx.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ran.epx.api.repository.CourseRepository;
import com.ran.epx.model.Course;

@Service
public class CourseService {

	@Autowired
	private CourseRepository courseRepository;
	
	public Course addCourse(Course course) {		
		Course result = courseRepository.save(course);
		return result;
	} 
}
