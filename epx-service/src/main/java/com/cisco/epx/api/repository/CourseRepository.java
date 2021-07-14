package com.cisco.epx.api.repository;


import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.cisco.epx.model.Course;

public interface CourseRepository extends MongoRepository<Course, String>{
	
	@Query("{ownerId : ?0}")
	List<Course> findCourseByOwnerId(String ownerId);
		
}