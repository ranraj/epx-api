package com.cisco.epx.api.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AnswerChoise{
    private String answerContent;
    private boolean isAnswer;     

}