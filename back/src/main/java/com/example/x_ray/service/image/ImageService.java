package com.example.x_ray.service.image;

import com.example.x_ray.dto.image.ImageDto;
import com.example.x_ray.dto.image.ResponseImageDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    public void saveImageName(List<MultipartFile> images,  ImageDto imageDto);
    public ImageDto getImageByImageName(String originalImageName);
    public ImageDto findImageById(Long id);
}
