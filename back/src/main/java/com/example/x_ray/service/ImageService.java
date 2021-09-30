package com.example.x_ray.service;

import com.example.x_ray.dto.imagedto.ImageDto;
import org.springframework.stereotype.Service;

@Service
public interface ImageService {
    public String saveImageName(ImageDto imageDto);
    public ImageDto getImageName();
}
