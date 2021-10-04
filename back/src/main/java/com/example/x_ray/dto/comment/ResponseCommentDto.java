package com.example.x_ray.dto.comment;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseCommentDto {

    private String content;
    private String userNickName;
    private Long postId;
}
