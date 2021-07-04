package com.cisco.epx.api.repository;


import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.cisco.epx.api.model.User;

public interface UserRepository extends MongoRepository<User, String>{
	@Query("{ '_id' : ?0 }")
    Optional<User> findById(String id);
	
	@Query("{email : ?0, password : ?1}")
	User findUserByEmailAndPassword(String email, String password);
}