package com.ran.epx.api.controller;

import java.util.List;
import java.util.Optional;

import javax.swing.text.html.Option;

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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ran.epx.api.dto.LoginRequest;
import com.ran.epx.api.error.ResponseError;
import com.ran.epx.api.repository.AuthTokenRepository;
import com.ran.epx.api.repository.UserRepository;
import com.ran.epx.api.service.AuthTokenGenerator;
import com.ran.epx.model.AuthToken;
import com.ran.epx.model.AuthTokenType;
import com.ran.epx.model.Course;
import com.ran.epx.model.User;

@RestController
@RequestMapping("/user")
public class UserController {

	public static final String USER_COLLECTION_NAME = "user";

	@Autowired
	private UserRepository usersRepository;

	@Autowired
	private AuthTokenRepository authTokenRepository;

	@GetMapping("/{id}")
	public ResponseEntity<Object> getUser(@PathVariable("id") String id) {

		Optional<User> responseEntity = usersRepository.findById(id);

		return new ResponseEntity<>(responseEntity, HttpStatus.OK);
	}

	@GetMapping("/search")
	public ResponseEntity<Object> getUserByEmail(@RequestParam("emailId") String emailId) {

		Optional<User> responseEntity = usersRepository.findUserByEmail(emailId);

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
	
	/**
	 * Create token if user is present
	 * If not Register user and creates the token
	 * @param loginRequest
	 * @param systemAuthToken
	 * @return
	 */
	@PostMapping("/token/oauth")
	public ResponseEntity<Object> loginOrSignup(@RequestBody LoginRequest loginRequest,
			@RequestHeader("authKey") String systemAuthToken) {

		ResponseEntity<Object> responseEntity =  createAccessToken(loginRequest,systemAuthToken);

		if (responseEntity.getStatusCode() == HttpStatus.OK || responseEntity.getStatusCode() == HttpStatus.UNAUTHORIZED) {
			return responseEntity;
		} else {

			User user = new User();
			user.setEmail(loginRequest.getEmail());
			user.setUsername(loginRequest.getUsername());
			user.setProvider(loginRequest.getProvider());
			register(user);
			return createAccessToken(loginRequest, systemAuthToken);
		}
	}

	@PostMapping("/token")
	public ResponseEntity<Object> createAccessToken(@RequestBody LoginRequest loginRequest,
			@RequestHeader("authKey") String systemAuthToken) {
		
		Optional<AuthToken> authTokenOption = authTokenRepository.findByToken(systemAuthToken);

		if (!authTokenOption.isPresent() || authTokenOption.get().getType() == AuthTokenType.USER) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		Optional<User> responseEntity = usersRepository.findUserByEmail(loginRequest.getEmail());
		//TODO : Must delete previous records only 5 Auth token can be created
		
		if (!responseEntity.isPresent()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		String token = AuthTokenGenerator.newAuthToken();
		AuthToken authToken = AuthToken.newToken(token);
		User user = responseEntity.get();
		authToken.setUserId(user.getId());
		authTokenRepository.save(authToken);

		return new ResponseEntity<>(authToken, HttpStatus.OK);

	}

	/**
	 * Validate Auth token
	 * 
	 * @param loginRequest
	 * @param authToken
	 * @return
	 */

	@GetMapping("/token")
	public ResponseEntity<Object> validateAccessToken(@RequestHeader("authKey") String authToken) {
				
		Optional<AuthToken> tokenOpt = authTokenRepository.findByToken(authToken);

		if (!tokenOpt.isPresent()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		AuthToken token = tokenOpt.get();
		if(token.isValid()) {
			return new ResponseEntity<>(token, HttpStatus.OK);
		}else {
			authTokenRepository.delete(token);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}

	@ExceptionHandler(DuplicateKeyException.class)
	public ResponseEntity<ResponseError> handleException(DuplicateKeyException e) {
		ResponseError error = new ResponseError(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
		return new ResponseEntity<>(error, error.getHttpStatus());
	}

}
