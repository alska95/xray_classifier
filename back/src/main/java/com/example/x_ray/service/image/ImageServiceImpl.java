package com.example.x_ray.service.image;

import com.example.x_ray.dto.image.ImageDto;
import com.example.x_ray.dto.image.ResponseImageDto;
import com.example.x_ray.entity.Image;
import com.example.x_ray.repository.image.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ImageServiceImpl implements ImageService{

    final ImageRepository imageRepository;

    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Transactional
    @Override
    public void saveImageName(ImageDto imageDto) {
        imageRepository.saveImages(imageDto);
    }

    @Transactional
    @Override
    public ImageDto getImageByImageName(String originalImageName) {
        Image image = imageRepository.getImage(originalImageName);
        ImageDto dto  = new ImageDto(
                image.getUser().getNickName(),
                image.getOriginalImageFileName(),
                image.getHeatmapImageFileName(),
                image.getCreatedDate()
        );
        return dto;
    }
}
