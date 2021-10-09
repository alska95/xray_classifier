package com.example.x_ray.controller;

import com.example.x_ray.dto.comment.RequestCommentDto;
import com.example.x_ray.dto.comment.ResponseCommentDto;
import com.example.x_ray.dto.image.ImageDto;
import com.example.x_ray.dto.post.RequestPostDto;
import com.example.x_ray.dto.post.ResponsePostDto;
import com.example.x_ray.service.image.ImageService;
import com.example.x_ray.service.post.PostService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest
@Transactional
@Slf4j
public class CommentControllerTest {

    @Autowired
    private CommentController commentController;
    @Autowired
    private PostController postController;
    @Autowired
    private ImageService imageService;


    @Test
    public void 포스트생성후찾아서댓글달기(){
        //given -- 포스트 생성
        String content = "이것은 생성 테스트 입니다.";
        String result = "임시 결과";
        String userName = "hwang";
        String originalImageName = "I:\\programming\\xray_classifier\\image\\20211004180548Effusion (2).jpg";
        RequestPostDto requestPostDto = new RequestPostDto(
                userName,
                originalImageName,
                content,
                result
        );

        ResponseEntity<ResponsePostDto> responsePostEntity = postController.addNewPost(requestPostDto);

        ImageDto image = imageService.getImageByImageName(originalImageName);
        String heatmapImageFileName = image.getHeatmapImageFileName();
        ResponsePostDto expectedResponse = new ResponsePostDto(
                responsePostEntity.getBody().getPostId(),
                userName,
                originalImageName,
                heatmapImageFileName,
                content,
                result,
                null
        );
        log.info("response.body.originalImage = [{}]" ,responsePostEntity.getBody().getOriginalImageName() );
        Assertions.assertThat(responsePostEntity.getBody()).isEqualTo(expectedResponse);
        Assertions.assertThat(responsePostEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<ResponsePostDto> selectedPost = postController.getPostById(responsePostEntity.getBody().getPostId());
        //when - comment 더하기

        String commentContent = "이것은 테스트용 댓글입니다.";
        String commentUser = "hwang";
        RequestCommentDto requestCommentDto = new RequestCommentDto(
                commentContent,
                commentUser,
                selectedPost.getBody().getPostId()
        );

        //then - comment가 해당 포스트에 재대로 추가되었는가??
        ResponseEntity<ResponseCommentDto> responseCommentDtoResponseEntity = commentController.addComment(requestCommentDto);
        Assertions.assertThat(responseCommentDtoResponseEntity.getBody().getContent()).isEqualTo(commentContent);
        Assertions.assertThat(responseCommentDtoResponseEntity.getBody().getUserNickName()).isEqualTo(commentUser);
    }
}