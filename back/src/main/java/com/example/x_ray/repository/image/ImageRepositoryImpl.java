package com.example.x_ray.repository.image;

import com.example.x_ray.dto.image.ImageDto;
import com.example.x_ray.entity.Image;
import com.example.x_ray.repository.user.UserRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class ImageRepositoryImpl implements ImageRepository{

    @PersistenceContext
    private EntityManager em;

    final UserRepository userRepository;

    public ImageRepositoryImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void saveImages(ImageDto imageDto) {
        Image image = new Image();
        image.setUser(userRepository.findUser(imageDto.getUserNickName()));
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
