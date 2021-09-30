package com.example.x_ray.controller;


import com.example.x_ray.dto.imagedto.ImageDto;
import com.example.x_ray.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;


//분석 끝나면 gradImage랑 Image같이 받자

@RestController
@Slf4j
public class ImageController {

    final ImageService imageService;
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }
    private String fileDir= "I:\\programming\\xray_classifier\\image\\";



    @PostMapping("/image")
    public String uploadImage(MultipartHttpServletRequest multipartHttpServletRequest){
        String src = multipartHttpServletRequest.getParameter("src");
        log.info("image src = [{}]" , src);
        List<MultipartFile> images = multipartHttpServletRequest.getFiles("file");
        log.info("images size = [{}]" , images.size());

        Date date = new Date();
        String fileNames[] = new String[2]; // 0: original 1: heatmap

        String path = fileDir;

        for(int i = 0 ; i < 2 ; i++){
            fileNames[i] = path+images.get(i).getOriginalFilename()+date.toString();
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
        ImageDto imageDto = new ImageDto(fileNames[0] , fileNames[1], date);
        return imageService.saveImageName(imageDto);
    }

//    @GetMapping("/image")
//    public String[] getImage(){
//        return imageService.getImageName();
//    }
}
