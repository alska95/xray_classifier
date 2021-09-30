package com.example.x_ray.service;

import com.example.x_ray.dto.imagedto.ImageDto;
import com.example.x_ray.repository.ImageRepository;

public class ImageServiceImpl implements ImageService{

    final ImageRepository imageRepository;

    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public String saveImageName(ImageDto imageDto) {

        return null;
    }

    @Override
    public ImageDto getImageName() {
        return null;
    }
}
