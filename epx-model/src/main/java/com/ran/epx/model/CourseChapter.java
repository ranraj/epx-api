package com.ran.epx.model;

import java.util.ArrayList;
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
    private ContentProvider contentProvider;
    private ChapterContent content;
    private String videoLink;
    private List<String> tags;                  
    private List<ChapterQuestion> examQuestions;   
    private float version;
    private boolean deleted;
    private String courseId;
    
    public ExamChapter getExamChapter() {
		ExamChapter examChapter = new ExamChapter();
		examChapter.setId(this.id);
		examChapter.setChapterId(this.id);
		examChapter.setVersion(this.version);
		examChapter.setCourseId(this.courseId);
		List<ExamChapterQuestion> answerSheet = new ArrayList<>();
		List<ChapterQuestion> chapterQuestions = this.getExamQuestions();
		if(chapterQuestions != null) {
			for (ChapterQuestion chapterQuestion : chapterQuestions) {			
				answerSheet.add(ExamChapterQuestion.from(chapterQuestion));
			}	
		}		
		examChapter.setQuestions(answerSheet);
		
		return examChapter;
	}
    
}