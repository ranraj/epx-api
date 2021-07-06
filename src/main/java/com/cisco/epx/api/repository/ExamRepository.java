package com.cisco.epx.api.repository;


import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.cisco.epx.api.model.ExamChapter;

public interface ExamRepository extends MongoRepository<ExamChapter, String>{
	
	@Query("{userId : ?0}")
	List<ExamChapter> findByUserId(String userId);
}