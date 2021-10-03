package com.example.x_ray.service.post;

import com.example.x_ray.dto.post.PostDto;
import com.example.x_ray.entity.Post;
import com.example.x_ray.repository.post.PostRepository;
import com.example.x_ray.service.image.ImageService;
import org.springframework.stereotype.Service;


@Service
public class PostServiceImpl implements PostService{

    final PostRepository postRepository;
    final ImageService imageService;

    public PostServiceImpl(PostRepository postRepository, ImageService imageService) {
        this.postRepository = postRepository;
        this.imageService = imageService;
    }

    @Override
    public PostDto getPostByNickName(String userNickName) {
        Post post = postRepository.getPost(userNickName);
        PostDto postDto = new PostDto(
/*                post.getContent(),
                post.getResult(),
                imageService.getImageByNickName(userNickName),
                userService.findUser(),
                commentService.findComment(),*/
        );

        return postDto;
    }

    @Override
    public void savePost(PostDto postDto) {
        postRepository.save(postDto);
    }
}
