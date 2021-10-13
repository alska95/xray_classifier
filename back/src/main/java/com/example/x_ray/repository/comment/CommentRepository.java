package com.example.x_ray.repository.comment;

import com.example.x_ray.dto.comment.CommentDto;
import com.example.x_ray.entity.Comment;

import java.util.List;

public interface CommentRepository {
    public List<Comment> getCommentsByPostId(Long postId);
    public Comment setCommentByPostId(CommentDto commentDto);
    public void deleteCommentsByPostId(Long postId);
    public void deleteCommentByCommentId(Long commentId);
    public Comment getCommentsByCommentId(Long commentId);
}
