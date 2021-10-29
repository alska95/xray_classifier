package com.example.x_ray.controller;


import com.example.x_ray.dto.image.ImageDto;
import com.example.x_ray.dto.image.ResponseImageDto;
import com.example.x_ray.dto.post.PostDto;
import com.example.x_ray.dto.post.ResponsePostDto;
import com.example.x_ray.service.image.ImageService;
import com.example.x_ray.service.post.PostService;
import com.example.x_ray.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


//분석 끝나면 gradImage랑 Image같이 받자

@RestController
@Slf4j
public class ImageController {

    final ImageService imageService;
    final PostService postService;
    final UserService userService;
    public ImageController(ImageService imageService, PostService postService, UserService userService) {
        this.imageService = imageService;
        this.postService = postService;
        this.userService = userService;
    }




    @PostMapping("/image")
    public ResponseEntity<ResponsePostDto> uploadImageAndPost(MultipartHttpServletRequest multipartHttpServletRequest){
        String userNickName = multipartHttpServletRequest.getParameter("userNickName");
        String src = multipartHttpServletRequest.getParameter("src");
        log.info("image src = [{}]" , src);
        List<MultipartFile> images = multipartHttpServletRequest.getFiles("file");
        log.info("images size = [{}]" , images.size());
        String resultList = multipartHttpServletRequest.getParameter("result");
        log.info("diagnosis result =[{}]", resultList);

        Date date = new Date();

        ImageDto imageDto = new ImageDto(userNickName, "" , "", date, resultList);
        imageService.saveImageName(images, imageDto);
        PostDto postDto = new PostDto(
                "",
                resultList,
                imageDto,
                userService.findUserByNickName(userNickName)
        );
        PostDto savedPostDto = postService.savePost(postDto);
        ResponsePostDto responsePostDto = new ResponsePostDto(
                savedPostDto.getPostId(),
                savedPostDto.getUser().getNickName(),
                savedPostDto.getImage().getOriginalImageFileName(),
                savedPostDto.getImage().getHeatmapImageFileName(),
                savedPostDto.getContent(),
                savedPostDto.getDiagnosisResult(),
                null
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(responsePostDto);
    }

    @GetMapping("/image/{originalImageName}")
    public ResponseImageDto getImage(@PathVariable String originalImageName){
        ImageDto image = imageService.getImageByImageName(originalImageName);
        ResponseImageDto responseImageDto = new ResponseImageDto(
                image.getOriginalImageFileName(),
                image.getHeatmapImageFileName()
        );
        return responseImageDto;
    }

    @GetMapping("/image/id/{id}")
    public ResponseImageDto findImageById(@PathVariable Long id){
        ImageDto image = imageService.findImageById(id);
        ResponseImageDto responseImageDto = new ResponseImageDto(
                image.getOriginalImageFileName(),
                image.getHeatmapImageFileName()
        );
        return responseImageDto;
    }
}
