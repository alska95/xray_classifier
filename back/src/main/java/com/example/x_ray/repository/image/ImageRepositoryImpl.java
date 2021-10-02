package com.example.x_ray.repository.image;

import com.example.x_ray.dto.image.ImageDto;
import com.example.x_ray.dto.image.RequestImageDto;
import com.example.x_ray.dto.user.UserDto;
import com.example.x_ray.entity.Image;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
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
    public Image getImage(String userNickName) {
        return em.createQuery("select i from Image i where i.user.nickName =: userNickName", Image.class)
                .setParameter("userNickName" , userNickName)
                .getResultList().get(0);
    }
}
