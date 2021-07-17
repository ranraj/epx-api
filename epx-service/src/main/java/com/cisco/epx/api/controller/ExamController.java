package com.cisco.epx.api.controller;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cisco.epx.api.repository.CourseChapterRepository;
import com.cisco.epx.api.repository.CourseRepository;
import com.cisco.epx.api.repository.ExamRepository;
import com.cisco.epx.api.repository.UserRepository;
import com.cisco.epx.model.ChapterQuestion;
import com.cisco.epx.model.CourseChapter;
import com.cisco.epx.model.ExamChapter;
import com.cisco.epx.model.ExamChapterQuestion;
import com.cisco.epx.model.TextSimilarityScore;

@RestController
@RequestMapping("/exams")
public class ExamController {
	
	private static final Logger logger = LoggerFactory.getLogger(ExamController.class);
	
	private static final String INVALID_COURSE = "Invalid Course";
	
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
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${text.similarity.service.url}")
	private String textSimilarityServiceUrl;	
	
	@PostMapping("/users/{userId}")
	public ResponseEntity<Object> addCourse(@PathVariable("userId") String userId, @RequestBody ExamChapter exam) throws Exception {
		if(exam == null) {
			throw new IllegalArgumentException(INVALID_EXAM_CONTENT);
		}
		//Data validation
		userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException(INVALID_COURSE));
		courseRepository.findById(exam.getCourseId()).orElseThrow(() -> new IllegalArgumentException(INVALID_COURSE));		
		CourseChapter chapter =  courseChapterRepository.findById(exam.getChapterId()).orElseThrow(() -> new IllegalArgumentException(INVALID_CHAPTER));
		
		Map<String, ChapterQuestion> questionAnswerKey = chapter.getExamQuestions().stream()
			      .collect(Collectors.toMap(ChapterQuestion::getId, Function.identity()));
		int totalQuestions = chapter.getExamQuestions().size();
		int totalAnswerdQuestions = 0;
		int invalidQuestionOnAnswerSheetCount=0;
		for(ExamChapterQuestion question :exam.getQuestions()) {
			ChapterQuestion chapterQuestion = questionAnswerKey.get(question.getQuestionId());

			if(chapterQuestion == null) {
				invalidQuestionOnAnswerSheetCount++;
			}else {
				//Text comparison
				
				TextSimilarityScore textSimilarityScore = getTextSimilarityScore(chapterQuestion.getAnswer(),question.getAnswer());
				if(textSimilarityScore == null) {
					return new ResponseEntity<>("Gradding System failure, Try after sometime",HttpStatus.INTERNAL_SERVER_ERROR);
				}
				double score =  textSimilarityScore.getSimilarity();
				
				if(score > 0.5F) {
					question.setPass(true);
				}	
				question.setScore(score);							
			}
			if(!question.getAnswer().equals("")) {
				totalAnswerdQuestions++;
			}
			
		}
		exam.setUserId(userId);
		ExamChapter examResult = examRepository.save(exam);
		String result = String.format("%s - \"Total Questions : %d\" ,\"Total Answered Questions\" , %d ; Invalid Question count : %d",
				exam.getChapterId(),totalQuestions,totalAnswerdQuestions,invalidQuestionOnAnswerSheetCount);
		logger.info(result);
		
		return new ResponseEntity<>(examResult, HttpStatus.OK);
	}
	
	private TextSimilarityScore getTextSimilarityScore(String text1,String text2) {
		try {
		String url = String.format("%s?&text1={q}&text2={q}&lang=en",textSimilarityServiceUrl);	
		
		ResponseEntity<TextSimilarityScore> response = restTemplate
				.getForEntity(url, TextSimilarityScore.class,text1,text2);
		return response.getBody();
		}catch(Exception e) {
			logger.warn(e.getMessage());
		}
		return null;
	}
	
	@GetMapping("/users/{userId}")
	public ResponseEntity<Object> getExams(@PathVariable("userId") String userId) {
		return new ResponseEntity<>(examRepository.findByUserId(userId), HttpStatus.OK);
	}
	
	@GetMapping("/users/{userId}/courses/{courseId}/chapters/{chapterId}")
	public ResponseEntity<Object> getExams(@PathVariable("userId") String userId,@PathVariable("courseId") String courseId,
			@PathVariable("chapterId") String chapterId) {
		return new ResponseEntity<>(examRepository.findByCourseAndChapterId(userId,courseId,chapterId), HttpStatus.OK);
	}
	
}
