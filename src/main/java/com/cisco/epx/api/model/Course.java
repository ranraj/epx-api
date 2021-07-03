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

    public Map<String, Object> generateMap() {
        Map<String, Object> map = new HashMap<>();
        if (ownerId != null) map.put("ownerId", ownerId);
        if (name != null) map.put("name", name);
        if (photo != null) map.put("photo", photo);
        if (tags != null) map.put("tags", tags);
        if (category != null) map.put("category", category);
        if (cost != 0) map.put("cost", cost);
        if (rating != 0) map.put("rating", rating);
        if (description != null) map.put("description", description);
        if (likedBy != null) map.put("likedBy", likedBy);
        return map;
    }

    public static Course toCourse(Map<String, Object> map) {
        Course course = new Course();

        course.setId((String) map.get("id"));
        course.setOwnerId((String) map.get("ownerId"));
        course.setName((String) map.get("name"));
        course.setPhoto((String) map.get("photo"));
        course.setTags((List<String>) map.get("tags"));
        course.setCategory((String) map.get("category"));
        course.setCost((Double) map.get("cost"));
        course.setRating(((Long) map.get("rating")).intValue());
        course.setDescription((String) map.get("description"));
        course.setLikedBy((List<String>) map.get("likedBy"));

        return course;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("ownerId", ownerId);
        map.put("name", name);
        map.put("photo", photo);
        map.put("tags", tags);
        map.put("category", category);
        map.put("cost", cost);
        map.put("rating", rating);
        map.put("description", description);
        map.put("likedBy", likedBy);
        return map;
    }
}
