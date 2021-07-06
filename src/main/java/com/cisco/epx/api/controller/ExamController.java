package com.cisco.epx.api.controller;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cisco.epx.api.model.ChapterQuestion;
import com.cisco.epx.api.model.CourseChapter;
import com.cisco.epx.api.model.ExamChapter;
import com.cisco.epx.api.model.ExamChapterQuestion;
import com.cisco.epx.api.repository.CourseChapterRepository;
import com.cisco.epx.api.repository.CourseRepository;
import com.cisco.epx.api.repository.ExamRepository;
import com.cisco.epx.api.repository.UserRepository;

@RestController
@RequestMapping("/exams")
public class ExamController {
	
	private static final String INVALID_COURSE = "Invalid Course";
	private static final String INVALID_USER = "Invalid User";
	private static final String INVALID_EXAM_CONTENT = "Invalid Exam content";
	private static final String INVALID_CHAPTER= "Invalid chapter Id";
	@Autowired
	private ExamRepository examRepository;
	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CourseChapterRepository courseChapterRepository;
	
	@PostMapping("/users/{userId}")
	public ResponseEntity<Object> addCourse(@PathVariable("userId") String userId, @RequestBody ExamChapter exam) {
		if(exam == null) {
			throw new IllegalArgumentException(INVALID_EXAM_CONTENT);
		}
		//Data validation
		userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException(INVALID_COURSE));
		courseRepository.findById(exam.getCourseId()).orElseThrow(() -> new IllegalArgumentException(INVALID_COURSE));		
		CourseChapter chapter =  courseChapterRepository.findById(exam.getChapterId()).orElseThrow(() -> new IllegalArgumentException(INVALID_CHAPTER));
		//Validate Question and Answers
//		Map<String,String> questionAnswerKey = new HashMap<>(); 
//		for (ChapterQuestion chapterQuestion : chapter.getExamQuestions()) {
//			questionAnswerKey.put
//		}
		
		Map<String, ChapterQuestion> questionAnswerKey = chapter.getExamQuestions().stream()
			      .collect(Collectors.toMap(ChapterQuestion::getId, Function.identity()));
		int totalQuestions = chapter.getExamQuestions().size();
		int totalAnswerdQuestions = 0;
		int invalidQuestionOnAnswerSheetCount=0;
		for(ExamChapterQuestion question :exam.getQuestions()) {
			ChapterQuestion chapterQuestion = questionAnswerKey.get(question.getQuestionId());
			if(chapterQuestion == null) {
				invalidQuestionOnAnswerSheetCount++;
			}else if(chapterQuestion.getAnswer().equals(question.getAnswer())) {
				question.setPass(true);
			}
			if(!question.getAnswer().equals("")) {
				totalAnswerdQuestions++;
			}
			
		}
		exam.setUserId(userId);
		examRepository.save(exam);
		String result = String.format("%s - \"Total Questions : %d\" ,\"Total Answered Questions\" , %d ; Invalid Question count : %d",
				exam.getChapterId(),totalQuestions,totalAnswerdQuestions,invalidQuestionOnAnswerSheetCount);
		System.out.println(result);
		return new ResponseEntity<>(exam, HttpStatus.OK);
	}
	
	@GetMapping("/users/{userId}")
	public ResponseEntity<Object> getExams(@PathVariable("userId") String userId) {
		return new ResponseEntity<>(examRepository.findByUserId(userId), HttpStatus.OK);
	}
	
}
