package com.example.x_ray.service.image;

import com.example.x_ray.dto.image.ImageDto;
import com.example.x_ray.dto.image.ResponseImageDto;
import com.example.x_ray.entity.Image;
import com.example.x_ray.repository.image.ImageRepository;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl implements ImageService{

    final ImageRepository imageRepository;

    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public void saveImageName(ImageDto imageDto) {
        imageRepository.saveImages(imageDto);
    }

    @Override
    public ImageDto getImageByNickName(String userNickName) {
        Image image = imageRepository.getImage(userNickName);
        ImageDto dto  = new ImageDto(
                image.getUser().getNickName(),
                image.getOriginalImageFileName(),
                image.getHeatmapImageFileName(),
                image.getCreatedDate()
        );
        return dto;
    }
}
