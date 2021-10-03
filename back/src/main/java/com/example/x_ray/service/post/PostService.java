package com.example.x_ray.service.post;

import com.example.x_ray.dto.post.PostDto;
import org.springframework.stereotype.Service;

public interface PostService {
    public PostDto getPostByNickName(String userNickName);
    public void savePost(PostDto postDto);
}
