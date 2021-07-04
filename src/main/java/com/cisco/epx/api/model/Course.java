package com.cisco.epx.api.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Accessors
public class Course {
	
    private String id;
    private String ownerId;
    private String name;
    private String photo;
    private List<String> tags;    
    private String category;
    private double cost;
    private int rating;
    private String description;
    private List<String> likedBy;    
}
