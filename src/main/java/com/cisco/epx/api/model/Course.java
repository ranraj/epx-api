package com.cisco.epx.api.model;

import java.util.List;

import org.springframework.data.annotation.Id;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors
public class Course {
	@Id
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
