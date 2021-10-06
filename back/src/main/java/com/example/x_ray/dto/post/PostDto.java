package com.example.x_ray.dto.post;

import com.example.x_ray.dto.comment.CommentDto;
import com.example.x_ray.dto.image.ImageDto;
import com.example.x_ray.dto.user.UserDto;
import com.example.x_ray.entity.Comment;
import com.example.x_ray.entity.Image;
import com.example.x_ray.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PostDto {
    private Long postId;
    private String content;
    private String diagnosisResult;
    private ImageDto image;
    private UserDto user;
//    private List<CommentDto> comments;

    public PostDto(){}
    public PostDto(String content, String diagnosisResult, ImageDto image, UserDto user){
        this.content = content;
        this.diagnosisResult = diagnosisResult;
        this.image = image;
        this.user = user;
    }
}
