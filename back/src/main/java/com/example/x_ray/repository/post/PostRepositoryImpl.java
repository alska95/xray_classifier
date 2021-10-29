package com.example.x_ray.repository.post;

import com.example.x_ray.dto.post.PostDto;
import com.example.x_ray.entity.*;
import com.example.x_ray.entity.QImage;
import com.example.x_ray.entity.QPost;
import com.example.x_ray.entity.QUser;
import com.example.x_ray.repository.comment.CommentRepository;
import com.example.x_ray.repository.image.ImageRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.x_ray.entity.QImage.*;
import static com.example.x_ray.entity.QPost.*;
import static com.example.x_ray.entity.QUser.*;

@Slf4j
@Repository
public class PostRepositoryImpl implements PostRepository {

    @PersistenceContext
    private EntityManager em;

    final JPAQueryFactory queryFactory;
    final ImageRepository imageRepository;
    final CommentRepository commentRepository;

    public PostRepositoryImpl(JPAQueryFactory queryFactory, ImageRepository imageRepository, CommentRepository commentRepository) {
        this.queryFactory = queryFactory;
        this.imageRepository = imageRepository;
        this.commentRepository = commentRepository;
    }


    @Override
    public Post save(PostDto postDto) {
        User user = queryFactory
                .selectFrom(QUser.user)
                .where(QUser.user.nickName.eq(postDto.getUser().getNickName()))
                .fetchFirst();
//        User user = em.createQuery("select u from User u where u.nickName =: nickName", User.class)
//                .setParameter("nickName", postDto.getUser().getNickName())
//                .getResultList()
//                .get(0);
        Image image = imageRepository.getImage(postDto.getImage().getOriginalImageFileName());

        Post post = new Post(
                postDto.getContent() , postDto.getDiagnosisResult() ,
                user, null,
                image
        );
        em.persist(post);
        return post;
    }

    @Override
    public List<Post> getAllPosts() {
        List<Post> reversedList = queryFactory
                .selectFrom(post)
                .leftJoin(post.image(), image)
                .orderBy(image.createdDate.desc())
                .fetch();
//        List<Post> resultList = em.createQuery("select p from Post p", Post.class)
//                .getResultList();
//        List<Post> reversedList = new ArrayList<>();
//        for(int i = resultList.size()-1 ; i >= 0 ; i--){
//            reversedList.add(resultList.get(i));
//        }
        log.info("reversedList size = [{}]" , reversedList.size());
        return reversedList;
    }

    @Override
    public List<Post> getPostByUserName(String userNickName) {
        return queryFactory
                .selectFrom(post)
                .leftJoin(post.user(), user)
                .where(user.nickName.eq(userNickName))
                .fetch();
//        return  em.createQuery("select p from Post p where p.user.nickName =: userNickName", Post.class)
//                .setParameter("userNickName", userNickName)
//                .getResultList();  //묵시적 조인이라 수정함
    }

    @Transactional
    @Override
    public Post getPostByPostId(Long postId) {
        return queryFactory
                .selectFrom(post)
                .where(post.id.eq(postId))
                .fetchFirst();
//        return em.createQuery("select p from Post p where p.id =: postId" , Post.class)
//                .setParameter("postId", postId)
//                .getResultList()
//                .get(0);
    }

    @Override
    public Post getPostByImageName(PostDto postDto){

        return queryFactory.selectFrom(post)
                .leftJoin(post.image(), image)
                .where(image.originalImageFileName
                        .eq(postDto.getImage().getOriginalImageFileName())
                )
                .fetchFirst();
//        return  em.createQuery("select p from Post p where " +
//                "p.image.originalImageFileName =: originalImageFileName", Post.class)
//                .setParameter("originalImageFileName", postDto.getImage().getOriginalImageFileName())
//                .getResultList()
//                .get(0);
    }

    @Override
    public Post updatePost(PostDto updatePostDto){
        Post certainPost = getPostByImageName(updatePostDto);
        log.info("post.getContent() = [{}]", updatePostDto.getContent());
        log.info("certainPost = [{}]", certainPost.getId());
        certainPost.setContent(updatePostDto.getContent());
//        certainPost.setComments(null);//수정필요 comment update는 comment로만.
        return certainPost;
    }

    @Transactional
    @Override
    public void deletePostById(Long id){
        Post post = getPostByPostId(id);
        post.setComments(null);
        commentRepository.deleteCommentsByPostId(id);
        log.info("post = [{}] " ,post.getComments());
        em.remove(post);
    }
}
