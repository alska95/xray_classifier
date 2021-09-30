package com.example.x_ray.repository;

import com.example.x_ray.dto.imagedto.ImageDto;
import com.example.x_ray.dto.imagedto.UserDto;
import com.example.x_ray.entity.Image;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository {
    /*임시 추후에 dto로 바꾸기*/
    public void saveImages(ImageDto imageDto);
    public List<Image> getImages(UserDto userDto);
}
