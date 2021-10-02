package com.example.x_ray.repository.post;


import com.example.x_ray.dto.post.PostDto;
import com.example.x_ray.dto.post.ResponsePostDto;
import com.example.x_ray.entity.Post;

public interface PostRepository {
    public void save(PostDto postDto);
    public Post getPost(String userId);
}
