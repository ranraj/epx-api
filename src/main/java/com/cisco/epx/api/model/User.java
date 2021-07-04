package com.cisco.epx.api.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Accessors(chain = true)
public class User {
    private String email;
    private String password;
    private String username;
    private List<String> favorites;    
}
