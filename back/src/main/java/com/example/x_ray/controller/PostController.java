package com.example.x_ray.controller;

import com.example.x_ray.dto.comment.CommentDto;
import com.example.x_ray.dto.post.PostDto;
import com.example.x_ray.dto.post.RequestPostDto;
import com.example.x_ray.dto.post.ResponsePostDto;
import com.example.x_ray.service.comment.CommentService;
import com.example.x_ray.service.image.ImageService;
import com.example.x_ray.service.post.PostService;
import com.example.x_ray.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public ResponsePostDto postDtoToResponseMapper(PostDto v){
        return new ResponsePostDto(
                v.getPostId(),
                v.getUser().getNickName(),
                v.getImage().getOriginalImageFileName(),
                v.getImage().getHeatmapImageFileName(),
                v.getContent(),
                v.getDiagnosisResult(),
                v.getComments()
        );
    }

    @PutMapping("/post")
    public ResponseEntity<ResponsePostDto> updatePost(@RequestBody RequestPostDto requestPostDto){
        PostDto postDto = new PostDto(
                requestPostDto.getContent(),
                requestPostDto.getDiagnosisResult(),
                imageService.getImageByImageName(requestPostDto.getOriginalImageName()),
                userService.findUserByNickName(requestPostDto.getUserNickName())
        );
        PostDto updatedPost = postService.updatePost(postDto);
        ResponsePostDto updatedResponse = postDtoToResponseMapper(updatedPost);
        return ResponseEntity.status(HttpStatus.OK).body(updatedResponse);
    }

    @PostMapping("/post")
    public ResponseEntity<ResponsePostDto> addNewPost(@RequestBody RequestPostDto requestPostDto){
        PostDto postDto = new PostDto(
                requestPostDto.getContent(),
                requestPostDto.getDiagnosisResult(),
                imageService.getImageByImageName(requestPostDto.getOriginalImageName()),
                userService.findUserByNickName(requestPostDto.getUserNickName())
        );
        PostDto savedPostDto = postService.savePost(postDto);
        ResponsePostDto savedResponse = postDtoToResponseMapper(savedPostDto);
        return ResponseEntity.status(HttpStatus.OK).body(savedResponse);
    }

    @GetMapping("/post/id/{postId}")
    public ResponseEntity<ResponsePostDto> getPostById(@PathVariable Long postId){
        PostDto postById = postService.getPostById(postId);
        ResponsePostDto responsePostDto = postDtoToResponseMapper(postById);
        return ResponseEntity.status(HttpStatus.OK).body(responsePostDto);
    }

    @GetMapping("/post/{userNickName}")
    public List<ResponsePostDto> findPostByUserNickName(@PathVariable String userNickName){
        List<PostDto> postDtos = postService.getPostByNickName(userNickName);
        List<ResponsePostDto> responsePostDtos = postDtos.stream().map( v-> postDtoToResponseMapper(v)
        ).collect(Collectors.toList());

        return responsePostDtos;
    }

    @GetMapping("/posts")
    public List<ResponsePostDto> findAllPosts(){
        List<PostDto> allPosts = postService.getAllPosts();
        List<ResponsePostDto> responsePostDtos = allPosts.stream().map( v-> postDtoToResponseMapper(v))
                .collect(Collectors.toList());
        return responsePostDtos;
    }

    @DeleteMapping("/post/id/{postId}")
    public ResponsePostDto deleteByPostId(@PathVariable Long postId){
        postService.deleteByPostId(postId);
        return null;
    }
}
