package com.example.x_ray.controller;


import com.example.x_ray.dto.image.ImageDto;
import com.example.x_ray.dto.image.ResponseImageDto;
import com.example.x_ray.dto.post.PostDto;
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
    private String saveDir = "I:\\programming\\xray_classifier\\front\\public\\img\\";



    @PostMapping("/image")
    public ResponseEntity<ResponseImageDto> uploadImage(MultipartHttpServletRequest multipartHttpServletRequest){
        String userNickName = multipartHttpServletRequest.getParameter("userNickName");
        String src = multipartHttpServletRequest.getParameter("src");
        log.info("image src = [{}]" , src);
        List<MultipartFile> images = multipartHttpServletRequest.getFiles("file");
        log.info("images size = [{}]" , images.size());
        String resultList = multipartHttpServletRequest.getParameter("result");
        log.info("diagnosis result =[{}]", resultList);

        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateString = format.format(date);
        String fileNames[] = new String[2]; // 0: original 1: heatmap
        String dbSaveNames[] = new String[2];

        String path = saveDir;

        for(int i = 0 ; i < 2 ; i++){
            fileNames[i] = path+dateString+images.get(i).getOriginalFilename();
            dbSaveNames[i] = "/img/" + dateString+images.get(i).getOriginalFilename();
            long fileSize = images.get(i).getSize();
            log.info("fileNames [{}] = [{}]", i , fileNames[i]);
            try{
                images.get(i).transferTo(new File(fileNames[i]));
            }catch (IllegalStateException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();;
            }
        }

        ImageDto imageDto = new ImageDto(userNickName, dbSaveNames[0] , dbSaveNames[1], date);
        imageService.saveImageName(imageDto);
        PostDto postDto = new PostDto(
                "",
                resultList,
                imageDto,
                userService.findUserByNickName(userNickName)
        );
        postService.savePost(postDto);
        ResponseImageDto responseImageDto = new ResponseImageDto(imageDto.getOriginalImageFileName() , imageDto.getHeatmapImageFileName());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseImageDto);
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
}
