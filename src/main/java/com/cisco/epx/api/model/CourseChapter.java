package com.cisco.epx.api.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors
@Document
public class CourseChapter {	
	@Id
    private String id;   
	private String name;
    private String title;
    private String videoLink;
    private List<String> tags;                  
    private List<ChapterQuestion> examQuestions;   
    private float version;
    private boolean deleted;
    private String courseId;
  
}