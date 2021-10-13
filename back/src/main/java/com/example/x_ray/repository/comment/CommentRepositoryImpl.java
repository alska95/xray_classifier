package com.example.x_ray.repository.comment;

import com.example.x_ray.dto.comment.CommentDto;
import com.example.x_ray.entity.Comment;
import com.example.x_ray.entity.Post;
import com.example.x_ray.entity.User;
import com.example.x_ray.repository.post.PostRepository;
import com.example.x_ray.repository.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Slf4j
public class CommentRepositoryImpl implements CommentRepository{


    final UserRepository userRepository;

    @PersistenceContext
    private EntityManager em;

    public CommentRepositoryImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<Comment> getCommentsByPostId(Long postId) {
        return em.createQuery("select c from Comment c where c.post.id =: commentsPostId", Comment.class)
                .setParameter("commentsPostId", postId)
                .getResultList();
    }

    @Override
    public Comment getCommentsByCommentId(Long commentId) {
        List<Comment> comment = em.createQuery("select c from Comment c where  c.id =: commentId", Comment.class)
                .setParameter("commentId", commentId)
                .getResultList();
        if(comment!= null)
            return comment.get(0);
        return null;
    }

    @Override
    public Comment setCommentByPostId(CommentDto commentDto) {
        User user = userRepository.findUser(commentDto.getUserNickName());
        Post post = null;
        List<Post> tmp = em.createQuery("select p from Post p where p.id =: id", Post.class)
                .setParameter("id", commentDto.getPostId())
                .getResultList();
        if(tmp!= null){
            post = tmp.get(0);

            Comment comment = new Comment(
                    commentDto.getContent(),
                    user,
                    post
            );
            em.persist(comment);
            return comment;
        }
        return null;

    }

    @Transactional
    @Override
    public void deleteCommentsByPostId(Long postId) {
//        em.createQuery("delete from Comment c where c.post.id =: postId ").setParameter("postId" , postId);
        List<Comment> commentsByPostId = getCommentsByPostId(postId);
        for(int i = 0 ; i < commentsByPostId.size() ;i++){
            em.remove(commentsByPostId.get(i));
        }
    }

    @Override
    public void deleteCommentByCommentId(Long commentId) {
/*        em.createQuery("delete from Comment c where c.id =: commentId")
                .setParameter("commentId", commentId);*/
        Comment comment = getCommentsByCommentId(commentId);
        em.remove(comment);
    }
}
