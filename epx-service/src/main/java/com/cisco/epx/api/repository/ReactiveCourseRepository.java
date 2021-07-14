package com.cisco.epx.api.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.cisco.epx.model.Course;

@Repository
public interface ReactiveCourseRepository extends ReactiveMongoRepository<Course,String>{

}
