package com.example.x_ray.repository.post;


import com.example.x_ray.dto.post.PostDto;
import com.example.x_ray.dto.post.PostSearchConditionDto;
import com.example.x_ray.entity.Post;

import java.util.List;

public interface PostRepository {
    public Post save(PostDto postDto);
    public List<Post> getAllPosts();
    public List<Post> getPostByUserName(String userNickName);
    public Post getPostByPostId(Long postId);
    public Post getPostByImageName(PostDto postDto);
    public Post updatePost(PostDto updatePostDto);
    public void deletePostById(Long id);
    public List<Post> findByCondition(PostSearchConditionDto condition);
}
