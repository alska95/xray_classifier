package com.example.x_ray.repository.image;

import com.example.x_ray.dto.image.ImageDto;
import com.example.x_ray.entity.Image;
import com.example.x_ray.entity.QImage;
import com.example.x_ray.repository.user.UserRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static com.example.x_ray.entity.QImage.*;

@Slf4j
@Repository
public class ImageRepositoryImpl implements ImageRepository{

    @PersistenceContext
    private EntityManager em;

    final UserRepository userRepository;
    final JPAQueryFactory queryFactory;

    public ImageRepositoryImpl(UserRepository userRepository, JPAQueryFactory queryFactory) {
        this.userRepository = userRepository;
        this.queryFactory = queryFactory;
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
    public Image getImage(String originalImageName) {
        log.info("originalImageName = [{}]", originalImageName);
        return queryFactory.selectFrom(image)
                .where(image.originalImageFileName.eq(originalImageName))
                .fetchFirst();
//        return em.createQuery("select i from Image i where i.originalImageFileName =: originalImageName", Image.class)
//                .setParameter("originalImageName" , originalImageName)
//                .getResultList().get(0);
    }
}
