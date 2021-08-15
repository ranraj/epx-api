package com.ran.epx.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors
public class Course {
	@Id
	private String id;	
	private String title;
	private String name;
	private String photo;
	private List<String> tags;
	private String category;
	private double cost;
	private int rating;
	private String description;
	private List<String> likedBy;
	private float version;
	private boolean deleted;
	private boolean published;

	@CreatedBy
	private String ownerId;
	@LastModifiedBy
	private String modifiedBy;
	
	@CreatedDate
	private Date createdDate;
	@LastModifiedDate
	private Date lastModifiedDate;

}
