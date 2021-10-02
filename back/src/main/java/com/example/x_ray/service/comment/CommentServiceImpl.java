package com.example.x_ray.service.comment;

import com.example.x_ray.dto.comment.CommentDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{
    @Override
    public List<CommentDto> getCommentByNickName(String nickName) {
        return null;
    }
}
