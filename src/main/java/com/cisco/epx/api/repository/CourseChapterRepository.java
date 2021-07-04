package com.cisco.epx.api.repository;


import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.cisco.epx.api.model.CourseChapter;

public interface CourseChapterRepository extends MongoRepository<CourseChapter, String>{
	
	@Query("{courseId : ?0}")
	List<CourseChapter> findByCourseId(String courseId);
	
	@Query("{courseId : ?0,chapterId : ?1}")
	List<CourseChapter> findByCourseIdAndChapterId(String courseId,String chapterId);
}