package com.example.x_ray.service.post;

import com.example.x_ray.dto.post.PostDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface PostService {
    public List<PostDto> getPostByNickName(String userNickName);
    public void savePost(PostDto postDto);
}
