package com.cisco.epx.api.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cisco.epx.api.dto.LoginRequest;
import com.cisco.epx.api.model.ResponseError;
import com.cisco.epx.api.model.User;
import com.cisco.epx.api.repository.UserRepository;

@RestController
@RequestMapping("/user")
public class UserController {

	public static final String USER_COLLECTION_NAME = "user";

	@Autowired
	private UserRepository usersRepository;

	@GetMapping("/{id}")
	public ResponseEntity<Object> getUser(@PathVariable("id") String id) {

		Optional<User> responseEntity = usersRepository.findById(id);

		return new ResponseEntity<>(responseEntity, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Object> register(@RequestBody User user) {
		User responseEntity = usersRepository.save(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(responseEntity);
	}

	@PutMapping
	public ResponseEntity<User> updateUser(@RequestBody User user) {
		User response = usersRepository.insert(user);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteUser(@PathVariable("id") String id) {
		usersRepository.deleteById(id);
		return new ResponseEntity<>(id, HttpStatus.OK);
	}

	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestBody LoginRequest loginRequest) {
		User responseEntity = usersRepository.findUserByEmailAndPassword(loginRequest.getEmail(),
				loginRequest.getPassword());
		if (responseEntity == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		responseEntity.setPassword(null);
		return new ResponseEntity<>(responseEntity, HttpStatus.OK);
	}

	@ExceptionHandler(DuplicateKeyException.class)
	public ResponseEntity<ResponseError> handleException(DuplicateKeyException e) {
		ResponseError error = new ResponseError(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
		return new ResponseEntity<>(error, error.getHttpStatus());
	}

}
