package com.example.x_ray.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final PostCheckInterceptor postCheckInterceptor;
    private final CommentCheckInterceptor commentCheckInterceptor;

    public WebConfig(PostCheckInterceptor postCheckInterceptor, CommentCheckInterceptor commentCheckInterceptor) {
        this.postCheckInterceptor = postCheckInterceptor;
        this.commentCheckInterceptor = commentCheckInterceptor;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("*")
                .allowCredentials(true);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInCheckInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/" , "/user/login" , "/user", "/logout", "/*.ico", "/error", "/**");
        registry.addInterceptor(postCheckInterceptor)
                .order(2)
                .addPathPatterns("/post","/post/**");
        registry.addInterceptor(commentCheckInterceptor)
                .order(3)
                .addPathPatterns("/comment/**");
    }

}
