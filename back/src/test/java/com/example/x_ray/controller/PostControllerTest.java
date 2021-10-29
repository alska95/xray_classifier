package com.example.x_ray.controller;

import com.example.x_ray.dto.image.ImageDto;
import com.example.x_ray.dto.post.RequestPostDto;
import com.example.x_ray.dto.post.ResponsePostDto;
import com.example.x_ray.service.image.ImageService;
import com.example.x_ray.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest
@Transactional
@Slf4j
public class PostControllerTest {

    @Autowired
    private PostController postController;
    @Autowired
    private UserService userService;
    @Autowired
    private ImageService imageService;

    @Test
    @Rollback
    public void 포스트생성테스트(){
        //given
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

        //when
        ResponseEntity<ResponsePostDto> responseEntity = postController.addNewPost(requestPostDto);

        //then
        ImageDto image = imageService.getImageByImageName(originalImageName);
        String heatmapImageFileName = image.getHeatmapImageFileName();
        ResponsePostDto expectedResponse = new ResponsePostDto(
                responseEntity.getBody().getPostId(),
                userName,
                originalImageName,
                heatmapImageFileName,
                content,
                result,
                null
        );
        log.info("response.body.originalImage = [{}]" ,responseEntity.getBody().getOriginalImageName() );
        Assertions.assertThat(responseEntity.getBody()).isEqualTo(expectedResponse);
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Rollback
    public void 유저가_가지고_있는_포스트_가져오기(){
        //given
        String userName = "hwang";
        //when
        List<ResponsePostDto> postByUserNickName = postController.findPostByUserNickName(userName);
        //then
        log.info("유저가 가지고 있는 포스트 개수 = [{}]" , postByUserNickName.size());
    }
    @Test
    @Rollback
    public void 포스트업데이트테스트(){
        //given
        String content = "이것은 생성 테스트 입니다.";
        String result = "임시 결과";
        String userName = "hwang";
        String originalImageName = "I:\\programming\\xray_classifier\\image\\20211004180548Effusion (2).jpg";
        RequestPostDto createdPostDto = new RequestPostDto(
                userName,
                originalImageName,
                content,
                result
        );
        postController.addNewPost(createdPostDto);
        //when
        String newContent = "이것은 업데이트 테스트 입니다.";

        //updateRequestPostDto의 이미지 이름과 매칭되는 post를 찾고, 내용들을 updateRequestPostDto의 필드들로 업데이트 한다.
        RequestPostDto updateRequestPostDto = new RequestPostDto(
                userName,
                originalImageName,
                newContent,
                result
        );
        ResponseEntity<ResponsePostDto> updateResponseEntity = postController.updatePost(updateRequestPostDto);

        //then
        Assertions.assertThat(updateResponseEntity.getBody().getContent()).isEqualTo(newContent);
    }
}
