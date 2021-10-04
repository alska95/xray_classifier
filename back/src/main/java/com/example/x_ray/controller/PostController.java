package com.example.x_ray.controller;

import com.example.x_ray.dto.post.PostDto;
import com.example.x_ray.dto.post.RequestPostDto;
import com.example.x_ray.dto.post.ResponsePostDto;
import com.example.x_ray.service.comment.CommentService;
import com.example.x_ray.service.image.ImageService;
import com.example.x_ray.service.post.PostService;
import com.example.x_ray.service.user.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PostController {

    final PostService postService;
    final ImageService imageService;
    final UserService userService;
    final CommentService commentService;

    public PostController(PostService postService, ImageService imageService, UserService userService, CommentService commentService) {
        this.postService = postService;
        this.imageService = imageService;
        this.userService = userService;
        this.commentService = commentService;
    }

    @PutMapping("/post")
    public void updatePost(@RequestBody RequestPostDto requestPostDto){
        PostDto postDto = new PostDto(
                requestPostDto.getContent(),
                requestPostDto.getDiagnosisResult(),
                imageService.getImageByImageName(requestPostDto.getOriginalImageName()),
                userService.findUserByNickName(requestPostDto.getUserNickName()),
                null
        );
        postService.updatePost(postDto);
    }
    @PostMapping("/post")
    public void addNewPost(@RequestBody RequestPostDto requestPostDto){
        PostDto postDto = new PostDto(
                requestPostDto.getContent(),
                requestPostDto.getDiagnosisResult(),
                imageService.getImageByImageName(requestPostDto.getOriginalImageName()),
                userService.findUserByNickName(requestPostDto.getUserNickName()),
                null
        );
        postService.savePost(postDto);
    }
    @GetMapping("/post/{userNickName}")
    public List<ResponsePostDto> findPostByUserNickName(@PathVariable String userNickName){
        List<PostDto> postDtos = postService.getPostByNickName(userNickName);
        List<ResponsePostDto> responsePostDtos = postDtos.stream().map( v-> new ResponsePostDto(
                v.getUser().getNickName(),
                v.getImage().getOriginalImageFileName(),
                v.getImage().getHeatmapImageFileName(),
                v.getContent(),
                v.getDiagnosisResult(),
                null
                )
        ).collect(Collectors.toList());

        return responsePostDtos;
    }
}
