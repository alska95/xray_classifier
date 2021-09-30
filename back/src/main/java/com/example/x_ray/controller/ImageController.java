package com.example.x_ray.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


//분석 끝나면 gradImage랑 Image같이 받자

@Controller
public class ImageController {

    private String fileDir;
/*
    @PostMapping("/image")
    public String uploadImage(@RequestPart("file") List<MultipartFile> file, HttpServletRequest request){
        List<MultipartFile> fileList =
    }*/

    @GetMapping("/image")
    public String getImage(){

    }
}
