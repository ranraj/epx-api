package com.ran.epx.api.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.ran.epx.model.Course;

public interface CourseRepository extends MongoRepository<Course, String> {

	@Query("{ownerId : ?0}")
	List<Course> findCourseByOwnerId(String ownerId);	

	Page<Course> findByPublished(boolean published, Pageable pageable);

	Page<Course> findByTitleContainingIgnoreCase(String title, Pageable pageable,boolean published);
}