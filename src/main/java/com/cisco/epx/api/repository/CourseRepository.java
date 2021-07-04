package com.cisco.epx.api.repository;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.cisco.epx.api.model.Course;

public interface CourseRepository extends MongoRepository<Course, Integer> {

}