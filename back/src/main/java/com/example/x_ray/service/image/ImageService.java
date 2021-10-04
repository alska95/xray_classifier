package com.example.x_ray.service.image;

import com.example.x_ray.dto.image.ImageDto;
import com.example.x_ray.dto.image.ResponseImageDto;

public interface ImageService {
    public void saveImageName(ImageDto imageDto);
    public ImageDto getImageByImageName(String originalImageName);
}
