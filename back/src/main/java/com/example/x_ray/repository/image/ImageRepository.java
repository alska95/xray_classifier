package com.example.x_ray.repository.image;

import com.example.x_ray.dto.image.ImageDto;
import com.example.x_ray.dto.user.UserDto;
import com.example.x_ray.entity.Image;

import java.util.List;

public interface ImageRepository {
    public void saveImages(ImageDto imageDto);
    public Image getImage(String originalImageName);
}
