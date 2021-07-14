package com.cisco.epx.api.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class LikeCourseDto {

    private String courseId;
    private String userId;
}
