package com.example.x_ray.security;

import com.example.x_ray.ConstVariable;
import com.example.x_ray.dto.user.UserDto;
import com.example.x_ray.entity.Post;
import com.example.x_ray.repository.post.PostRepository;
import com.example.x_ray.repository.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Slf4j
@Component
public class PostCheckInterceptor implements HandlerInterceptor {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostCheckInterceptor(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String method = request.getMethod();
        if(method.equals("DELETE")){
            String requestURL = request.getRequestURL().toString();
            HttpSession session = request.getSession();
            UserDto user = (UserDto)session.getAttribute(ConstVariable.LOGIN_MEMBER);
            int targetIndex = requestURL.indexOf("/id/")+4;
            Long targetId = Long.parseLong(requestURL.substring(targetIndex));
            Long postUserId = postRepository.getPostByPostId(targetId).getUser().getId();

            Long userId = userRepository.findUser(user.getNickName()).getId();

            if(userId == postUserId)
                return true;
            return false;
        }else if(method.equals("PUT")){
            HttpSession session = request.getSession();
            UserDto user = (UserDto)session.getAttribute(ConstVariable.LOGIN_MEMBER);
            String postNickName = request.getParameter("userNickName");
            String userNickName = user.getNickName();

            if(postNickName.equals(userNickName))
                return true;
            return false;
        }
        return true;
    }
}
