package com.ran.epx.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.experimental.Accessors;

@Document
@Data
@Accessors
public class AuthToken {

	@Id
	String id;
	@Indexed(unique = true)
	private String token;
	private String registeredName; // Register name is only for System and Client
	private String userId;
	private LocalDate createdDate;
	private int expiry; // In Days Never expire if negative integer sets
	private AuthTokenType type;
	private boolean isBlocked;
	private String blockedReason;

	public static AuthToken newToken(String token) {
		AuthToken authToken = new AuthToken();
		authToken.token = token;
		authToken.createdDate = LocalDate.now();
		authToken.expiry = 10; // Default 10 days
		authToken.type = AuthTokenType.USER; // Default is User token
		return authToken;
	}

	public AuthToken setExpiryDays(int expiryDays) {
		this.expiry = expiryDays;
		return this;
	}

	public AuthToken setAuthTokenType(AuthTokenType type) {
		this.type = type;
		return this;
	}

	public AuthToken setUserId(String userId) {
		this.userId = userId;
		return this;
	}

	public AuthToken setRegisteredName(String registeredName) {
		this.registeredName = registeredName;
		return this;
	}

	// TODO : Test coverage
	public boolean isValid() {		
		if (expiry < 0 || createdDate.plusDays(expiry).compareTo(createdDate) > 0) {
			return true;
		} else {
			return false;
		}
	}
}
