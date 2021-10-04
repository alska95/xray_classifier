package com.example.x_ray.repository.post;


import com.example.x_ray.dto.post.PostDto;
import com.example.x_ray.dto.post.ResponsePostDto;
import com.example.x_ray.entity.Post;

import java.util.List;

public interface PostRepository {
    public void save(PostDto postDto);
    public List<Post> getPost(String userNickName);
    public Post getCertainPost(PostDto postDto);
    public Post updatePost(PostDto updatePostDto);
}
