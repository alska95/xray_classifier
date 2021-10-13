package com.example.x_ray.service.comment;

import com.example.x_ray.dto.comment.CommentDto;
import com.example.x_ray.entity.Comment;
import com.example.x_ray.repository.comment.CommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService{

    final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public CommentDto commentToCommentDtoMapper(Comment comment){
        return new CommentDto(
                comment.getContent(),
                comment.getUser().getNickName(),
                comment.getPost().getId()
        );
    }
    @Override
    public List<CommentDto> getCommentByPostId(Long postId) {
        List<Comment> commentsByPostId = commentRepository.getCommentsByPostId(postId);
        return commentsByPostId.stream().map(v->commentToCommentDtoMapper(v)).collect(Collectors.toList());
    }


    @Transactional
    @Override
    public CommentDto addCommentToPost(CommentDto commentDto) {
        Comment comment = commentRepository.setCommentByPostId(commentDto);
        if(comment != null)
            return commentToCommentDtoMapper(comment);
        return null;
    }

    @Transactional
    @Override
    public void deleteComment(Long commentId) {
        commentRepository.deleteCommentByCommentId(commentId);
    }
}
