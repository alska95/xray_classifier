package com.example.x_ray.repository.comment;

import com.example.x_ray.dto.comment.CommentDto;
import com.example.x_ray.entity.Comment;
import com.example.x_ray.entity.Post;
import com.example.x_ray.entity.User;
import com.example.x_ray.repository.post.PostRepository;
import com.example.x_ray.repository.user.UserRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CommentRepositoryImpl implements CommentRepository{


    final PostRepository postRepository;
    final UserRepository userRepository;

    @PersistenceContext
    private EntityManager em;

    public CommentRepositoryImpl(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Comment> getCommentsByPostId(Long postId) {
        return em.createQuery("select c from Comment c where Post.id =: commentsPostId", Comment.class)
                .setParameter("commentsPostId", postId)
                .getResultList();
    }

    @Override
    public Comment setCommentByPostId(CommentDto commentDto) {
        User user = userRepository.findUser(commentDto.getUserNickName());
        Post post = postRepository.getPostByPostId(commentDto.getPostId());
        Comment comment = new Comment(
                commentDto.getContent(),
                user,
                post
        );
        em.persist(comment);
        return comment;
    }
}
