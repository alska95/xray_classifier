package com.example.x_ray.service.comment;

import com.example.x_ray.dto.comment.CommentDto;

import java.util.List;

public interface CommentService {
    public List<CommentDto> getCommentByPostId(String postId) ;
}
