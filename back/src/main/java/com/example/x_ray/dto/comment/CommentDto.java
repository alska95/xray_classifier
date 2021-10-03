package com.example.x_ray.dto.comment;


import com.example.x_ray.entity.Post;
import com.example.x_ray.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@AllArgsConstructor
public class CommentDto {

    private String content;
    private String userNickName;
    private Long postId;
}
