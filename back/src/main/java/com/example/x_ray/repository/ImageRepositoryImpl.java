package com.example.x_ray.repository;

import com.example.x_ray.dto.imagedto.ImageDto;
import com.example.x_ray.dto.imagedto.UserDto;
import com.example.x_ray.entity.Image;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class ImageRepositoryImpl implements ImageRepository{

    @PersistenceContext
    private EntityManager em;

    @Override
    public void saveImages(ImageDto imageDto) {
        Image image = new Image();
        image.setHeatmapImageFileName(imageDto.getHeatmapImageFileName());
        image.setOriginalImageFileName(imageDto.getOriginalImageFileName());
        image.setCreatedDate(imageDto.getCreatedDate());
        em.persist(image);
    }

    @Override
    public List<Image> getImages(UserDto userDto) {
        return em.createQuery("select i from Image i where i.user.id =: id").getResultList();
    }
}
