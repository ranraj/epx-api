package com.ran.epx.api.repository;


import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.ran.epx.model.ExamChapter;

public interface ExamRepository extends MongoRepository<ExamChapter, String>{
	
	@Query("{userId : ?0}")
	List<ExamChapter> findByUserId(String userId);
	
	@Query("{userId : ?0,courseId: ?1, chapterId : ?2}")
	List<ExamChapter> findByCourseAndChapterId(String userId,String courseId,String chapterId);
}