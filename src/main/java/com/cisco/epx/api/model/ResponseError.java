package com.cisco.epx.api.model;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ResponseError {
    private final HttpStatus httpStatus;
    private final String message;
}