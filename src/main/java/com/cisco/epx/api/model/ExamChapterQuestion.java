package com.cisco.epx.api.model;

import java.util.List;

import org.springframework.data.annotation.Id;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors
public class ExamChapterQuestion {
	@Id
	private double score;
	private String questionId;
	private String answer;
	private List<String> answers;
	private boolean isPass;
	private float version;	
	
}
