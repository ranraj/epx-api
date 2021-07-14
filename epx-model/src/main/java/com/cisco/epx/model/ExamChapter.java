package com.cisco.epx.model;

import java.util.List;

import org.springframework.data.annotation.Id;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors
public class ExamChapter {
	@Id
	private String id;
	private String chapterId;
	private String courseId;
	private float version;
	private String status;	// Iscompleted, inprogress, New
	private List<ExamChapterQuestion> questions;	
	private String userId;
	
}
