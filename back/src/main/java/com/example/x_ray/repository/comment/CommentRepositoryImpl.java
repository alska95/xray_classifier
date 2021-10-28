package com.example.x_ray.repository.comment;

import com.example.x_ray.dto.comment.CommentDto;
import com.example.x_ray.entity.*;
import com.example.x_ray.entity.QComment;
import com.example.x_ray.entity.QPost;
import com.example.x_ray.repository.post.PostRepository;
import com.example.x_ray.repository.user.UserRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static com.example.x_ray.entity.QComment.*;
import static com.example.x_ray.entity.QPost.*;
import static com.example.x_ray.entity.QPost.post;

@Repository
@Slf4j
public class CommentRepositoryImpl implements CommentRepository{

    final UserRepository userRepository;
    final JPAQueryFactory queryFactory;

    @PersistenceContext
    private EntityManager em;

    public CommentRepositoryImpl(UserRepository userRepository, JPAQueryFactory queryFactory) {
        this.userRepository = userRepository;
        this.queryFactory = queryFactory;
    }

    @Override
    public List<Comment> getCommentsByPostId(Long postId) {

        return queryFactory
                .selectFrom(comment)
                .where(comment.post().id.eq(postId))
                .fetch();
//        return em.createQuery("select c from Comment c where c.post.id =: commentsPostId", Comment.class)
//                .setParameter("commentsPostId", postId)
//                .getResultList();
    }


    @Override
    public Comment getCommentsByCommentId(Long commentId) {
        return queryFactory
                .selectFrom(comment)
                .where(comment.id.eq(commentId))
                .fetchFirst();
//        List<Comment> comment = em.createQuery("select c from Comment c where  c.id =: commentId", Comment.class)
//                .setParameter("commentId", commentId)
//                .getResultList();
//        if(comment!= null)
//            return comment.get(0);
//        return null;
    }

    @Override
    public Comment setCommentByPostId(CommentDto commentDto) {
        User user = userRepository.findUser(commentDto.getUserNickName());
        Post post = null;
        try{
            List<Post> tmp = queryFactory.selectFrom(QPost.post)
                    .where(QPost.post.id.eq(commentDto.getPostId()))
                    .fetch();
//        List<Post> tmp = em.createQuery("select p from Post p where p.id =: id", Post.class)
//                .setParameter("id", commentDto.getPostId())
//                .getResultList();
            if(!tmp.isEmpty() || tmp!=null){
                post = tmp.get(0);
                Comment comment = new Comment(
                        commentDto.getContent(),
                        user,
                        post
                );
                em.persist(comment);
                return comment;
            }
        }catch (Exception e){
            return null;
        }
        return null;
    }

    @Transactional
    @Override
    public void deleteCommentsByPostId(Long postId) {
        long deleteCount = queryFactory
                .delete(comment)
                .where(comment.post().id.eq(postId))
                .execute();
//        em.createQuery("delete from Comment c where c.post.id =: postId ")
//                .setParameter("postId" , postId)
//                .executeUpdate();
/*        List<Comment> commentsByPostId = getCommentsByPostId(postId);
        for(int i = 0 ; i < commentsByPostId.size() ;i++){
            em.remove(commentsByPostId.get(i));
        }*/
    }

    @Override
    public void deleteCommentByCommentId(Long commentId) {
/*        em.createQuery("delete from Comment c where c.id =: commentId")
                .setParameter("commentId", commentId);*/
        Comment comment = getCommentsByCommentId(commentId);
        em.remove(comment);
    }
}
