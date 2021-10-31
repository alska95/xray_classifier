package com.example.x_ray.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class PostSearchConditionDto {
    private String type;
    private String userName;
    private String diagnosisResult;
    private Date createdDateStart;
    private Date createdDateEnd;
}
