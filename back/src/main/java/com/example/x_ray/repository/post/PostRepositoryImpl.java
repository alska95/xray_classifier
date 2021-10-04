package com.example.x_ray.repository.post;

import com.example.x_ray.dto.post.PostDto;
import com.example.x_ray.dto.post.ResponsePostDto;
import com.example.x_ray.entity.Comment;
import com.example.x_ray.entity.Image;
import com.example.x_ray.entity.Post;
import com.example.x_ray.entity.User;
import com.example.x_ray.repository.image.ImageRepository;
import com.example.x_ray.repository.image.ImageRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;

@Slf4j
@Repository
public class PostRepositoryImpl implements PostRepository {

    @PersistenceContext
    private EntityManager em;

    final ImageRepository imageRepository;

    public PostRepositoryImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }


    @Override
    public void save(PostDto postDto) {
        User user = em.createQuery("select u from User u where u.nickName =: nickName", User.class)
                .setParameter("nickName", postDto.getUser().getNickName())
                .getResultList()
                .get(0);
        Image image = imageRepository.getImage(postDto.getImage().getOriginalImageFileName());

        Post post = new Post(
                postDto.getContent() , postDto.getDiagnosisResult() ,
                user, null,
                image
        );
        em.persist(post);
    }

    @Override
    public List<Post> getPost(String userNickName) {

        return  em.createQuery("select p from Post p where p.user.nickName =: userNickName", Post.class)
                .setParameter("userNickName", userNickName)
                .getResultList();
    }

    @Override
    public Post getCertainPost(PostDto postDto){

        return  em.createQuery("select p from Post p where " +
                "p.image.originalImageFileName =: originalImageFileName", Post.class)
                .setParameter("originalImageFileName", postDto.getImage().getOriginalImageFileName())
                .getResultList()
                .get(0);
    }

    @Override
    public Post updatePost(PostDto updatePostDto){
        Post certainPost = getCertainPost(updatePostDto);
        log.info("post.getContent() = [{}]", updatePostDto.getContent());
        log.info("certainPost = [{}]", certainPost.getId());
        certainPost.setContent(updatePostDto.getContent());
        certainPost.setComments(null);//수정필요
        return certainPost;
    }
}
