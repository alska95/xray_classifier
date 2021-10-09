package com.example.x_ray.service.post;

import com.example.x_ray.dto.image.ImageDto;
import com.example.x_ray.dto.post.PostDto;
import com.example.x_ray.dto.user.UserDto;
import com.example.x_ray.entity.Post;
import com.example.x_ray.repository.post.PostRepository;
import com.example.x_ray.service.comment.CommentService;
import com.example.x_ray.service.image.ImageService;
import com.example.x_ray.service.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class PostServiceImpl implements PostService{

    final PostRepository postRepository;
    final ImageService imageService;
    final UserService userService;
    final CommentService commentService;

    public PostServiceImpl(PostRepository postRepository, ImageService imageService, UserService userService, CommentService commentService) {
        this.postRepository = postRepository;
        this.imageService = imageService;
        this.userService = userService;
        this.commentService = commentService;
    }


    public PostDto postToPostDtoMapper(Post v) {
        {
            ImageDto imageDto = new ImageDto(
                    v.getImage().getOriginalImageFileName(),
                    v.getImage().getHeatmapImageFileName(),
                    v.getImage().getCreatedDate()
            );
            UserDto userDto = new UserDto(
                    v.getUser().getNickName(),
                    v.getUser().getEmail(),
                    v.getUser().getPassword()
            );
            PostDto postDto = new PostDto(
                    v.getId(),
                    v.getContent(),
                    v.getResult(),
                    imageDto,
                    userDto
            );
            return postDto;
        }
    }
    @Transactional
    @Override
    public List<PostDto> getPostByNickName(String userNickName) {
        List<Post> posts = postRepository.getPost(userNickName);
        List<PostDto> postDtos = new ArrayList<>();

        posts.stream().forEach(v->{
            postDtos.add(postToPostDtoMapper(v));
        });

        return postDtos;
    }
    @Transactional
    @Override
    public PostDto savePost(PostDto postDto) {
        Post savedPost = postRepository.save(postDto);
        PostDto savedPostDto = postToPostDtoMapper(savedPost);
        return savedPostDto;
    }

    @Transactional
    @Override
    public PostDto updatePost(PostDto postDto) {
        Post updatedPost = postRepository.updatePost(postDto);
        PostDto updatedPostDto = postToPostDtoMapper(updatedPost);
        return updatedPostDto;
    }
}