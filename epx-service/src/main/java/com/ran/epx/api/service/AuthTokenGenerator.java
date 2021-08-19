package com.ran.epx.api.service;

import java.util.UUID;

public class AuthTokenGenerator {

	public static String newAuthToken() {
		return UUID.randomUUID().toString();
	}
}
