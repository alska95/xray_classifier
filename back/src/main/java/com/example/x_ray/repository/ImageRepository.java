package com.example.x_ray.repository;

import com.example.x_ray.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.PersistenceContext;

@Repository
public interface ImageRepository {
    /*임시 추후에 dto로 바꾸기*/
    public void saveImages(String originalImageName, String heatmapImageName);
    public void findOriginalImage(User user);
    public void findHeatmapImage(User user);
}
