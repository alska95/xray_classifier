package com.example.x_ray.dto.post;

import com.example.x_ray.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ResponsePostDto {
    private Long postId;
    private String userNickName;
    private String originalImageName;
    private String heatmapImageName;
    private String content;
    private String diagnosisResult;
    private List<Comment> comments;
}
