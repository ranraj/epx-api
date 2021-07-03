package com.cisco.epx.api.model;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors
public class ExamQuestion {
    
    private Integer id;
    private String description;
    private String courseId;            
    private AnswerType answerType;
    private List<AnswerChoise> choices;
    private String answer;
    private String createdBy;
    //private Date created;
    //private Date updated;
    private String updatedBy;

    public static ExamQuestion toExamQuestion(Map<String, Object> map) {
    	ExamQuestion examQuestion = new ExamQuestion();

    	examQuestion.id = (Integer) map.get("id");
    	examQuestion.description = (String) map.get("description");
    	examQuestion.courseId = (String) map.get("courseId");
    	String answerType = (String) map.get("answerType");
    	examQuestion.answerType = AnswerType.fromString(answerType);
        //user.favorites = (List<String>) map.get("favorites");
    	examQuestion.answer = (String) map.get("answer");
    	examQuestion.createdBy = (String) map.get("createdBy");
    	examQuestion.updatedBy = (String) map.get("updatedBy");
        return examQuestion;
    } 
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();

        map.put("id", id);
        map.put("description", description);
        map.put("courseId", courseId);
        map.put("answerType", answerType);
        map.put("answer", answer);
        map.put("createdBy", createdBy);
        map.put("updatedBy", updatedBy);

        return map;
    }

    public Map<String, Object> generateMap() {
        Map<String, Object> map = new HashMap<>();

        if (id != null)
            map.put("id", id);
        if (description != null)
            map.put("description", description);
        if (courseId != null)
            map.put("courseId", courseId);
        if (answerType != null)
            map.put("answerType", answerType);
        if (answer != null)
            map.put("answer", answer);
        if (createdBy != null)
            map.put("createdBy", createdBy);
        if (updatedBy != null)
            map.put("updatedBy", updatedBy);
        return map;
    }
}
