package com.example.x_ray.security;

import com.example.x_ray.ConstVariable;
import com.example.x_ray.dto.user.UserDto;
import com.example.x_ray.repository.comment.CommentRepository;
import com.example.x_ray.repository.user.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class CommentCheckInterceptor implements HandlerInterceptor {
    final CommentRepository commentRepository;
    final UserRepository userRepository;

    public CommentCheckInterceptor(CommentRepository commentRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        UserDto user = (UserDto) session.getAttribute(ConstVariable.LOGIN_MEMBER);
        StringBuffer requestURL = request.getRequestURL();
        Long commentOwnerId = commentRepository.getCommentsByCommentId(
                Long.parseLong(requestURL.substring(requestURL.lastIndexOf("/id/")+4)))
                .getUser().getId();
        Long userId = userRepository.findUser(user.getNickName()).getId();
        if(commentOwnerId == userId)
            return true;
        return false;
    }
}
