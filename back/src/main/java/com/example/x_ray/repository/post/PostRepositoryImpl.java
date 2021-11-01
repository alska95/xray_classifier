package com.example.x_ray.repository.post;

import com.example.x_ray.dto.post.PostDto;
import com.example.x_ray.dto.post.PostSearchConditionDto;
import com.example.x_ray.entity.*;
import com.example.x_ray.entity.QUser;
import com.example.x_ray.repository.comment.CommentRepository;
import com.example.x_ray.repository.image.ImageRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.List;

import static com.example.x_ray.entity.QImage.*;
import static com.example.x_ray.entity.QPost.*;
import static com.example.x_ray.entity.QUser.*;
import static org.springframework.util.StringUtils.*;

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

    /**
     *     private String type;
     *     private String userName;
     *     private String diagnosisResult;
     *     private Date createdDateStart;
     *     private Date createdDateEnd;
     * }
     * */

    @Override
    public List<Post> findByCondition(PostSearchConditionDto condition) {
        JPAQuery<Post> postJPAQuery = queryFactory.selectFrom(post);
        log.info("condition type = [{}]" , condition.getType());
        if(condition.getType() != null){
            if(condition.getType().equals("name"))
                return postJPAQuery
                        .leftJoin(post.user(), user)
                        .where(dateBtw(condition), nameEq(condition))
                        .fetch();
            if(condition.getType().equals("result"))
                return postJPAQuery
                        .leftJoin(post.image() , image)
                        .where(dateBtw(condition), resultEq(condition))
                        .fetch();
        }
         //condition.getType.equals("all") //모든 field 사용자 입력값으로 통일됨.
        return postJPAQuery
                    .leftJoin(post.image(),image)
                    .leftJoin(post.user(), user)
                    .where(searchAll(condition), dateBtw(condition))
                    .fetch();
    }

    private BooleanExpression searchAll(PostSearchConditionDto condition){

        if(hasText(condition.getDiagnosisResult())){
            StringBuilder sb =new StringBuilder();
            String nameCondition = sb.append("%").append(condition.getUserName()).append("%").toString();
            return user.nickName.like(nameCondition).or(image.diseaseName.eq(condition.getDiagnosisResult()));
        }
        return null;
    }
    private BooleanExpression resultEq(PostSearchConditionDto condition){
        return hasText(condition.getDiagnosisResult()) ? image.diseaseName.eq(condition.getDiagnosisResult()) : null;
    }

    private BooleanExpression nameEq(PostSearchConditionDto condition){
        return hasText(condition.getUserName()) ? user.nickName.eq(condition.getUserName()) : null;
    }

    private BooleanExpression dateBtw(PostSearchConditionDto condition){
        if(condition.getCreatedDateStart() != null && condition.getCreatedDateEnd() != null)
            return image.createdDate.between(condition.getCreatedDateStart() , condition.getCreatedDateEnd());
        else if(condition.getCreatedDateEnd() != null)
            return image.createdDate.goe(condition.getCreatedDateStart());
        else if(condition.getCreatedDateStart() != null)
            return image.createdDate.loe(condition.getCreatedDateEnd());
        else
            return null;
    }
}
