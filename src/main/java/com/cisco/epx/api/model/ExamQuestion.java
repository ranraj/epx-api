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
}
