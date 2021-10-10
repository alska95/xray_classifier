package com.example.x_ray.service.post;

import com.example.x_ray.dto.post.PostDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface PostService {
    public List<PostDto> getAllPosts();
    public List<PostDto> getPostByNickName(String userNickName);
    public PostDto savePost(PostDto postDto);
    public PostDto updatePost(PostDto postDto);
    public PostDto getPostById(Long id);


}
