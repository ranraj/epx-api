package com.ran.epx.api.dto;

import com.ran.epx.model.Provider;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class LoginRequest {

    private String email;
    private String username;
    private String password;
    private Provider provider;
}
