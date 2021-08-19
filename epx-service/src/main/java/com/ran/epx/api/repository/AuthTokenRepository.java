package com.ran.epx.api.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.ran.epx.model.AuthToken;

public interface AuthTokenRepository extends MongoRepository<AuthToken, String> {

	@Query("{token : ?0}")
	Optional<AuthToken> findByToken(String token);	
}