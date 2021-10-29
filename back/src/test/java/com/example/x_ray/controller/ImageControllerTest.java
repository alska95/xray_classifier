package com.example.x_ray.controller;

import com.example.x_ray.dto.post.ResponsePostDto;
import com.example.x_ray.dto.user.RequestLoginDto;
import org.assertj.core.api.Assertions;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest
@Transactional
public class ImageControllerTest {

    @Autowired
    private ImageController imageController;
    @Autowired
    private UserController userController;

    @Test
    @Rollback
    public void 분석완료후이미지와게시물등록() throws IOException {
        //given
        MockHttpServletRequest loginRequest = new MockHttpServletRequest();
        userController.loginUser(
                new RequestLoginDto(
                        "hwang" , "hwang"
                ), loginRequest
        );
        MockMultipartHttpServletRequest request = new MockMultipartHttpServletRequest();
        FileInputStream fileInputStream = new FileInputStream(new File( "I:\\programming\\xray_classifier\\image\\Effusion (2).jpg"));
        MockMultipartFile originalFile = new MockMultipartFile(
                "file", "Effusion (2).jpg",
                "jpg",fileInputStream
        );
        request.addFile(originalFile);
        FileInputStream fileInputStream2 = new FileInputStream(new File( "I:\\programming\\xray_classifier\\image\\Fibrosis (1).jpg"));
        MockMultipartFile heatMapFile = new MockMultipartFile(
                "file", "Fibrosis (1).jpg",
                "jpg",fileInputStream2
        );

        request.addFile(heatMapFile);
        request.addParameter("src", "is working?");
        request.addParameter("userNickName", "hwang");
        request.addParameter("result" , "0.0342452,0.9891014,0.0088289,0.0015494,0.2037666,0.0155353,0.0124964,0.0077972,0.0700025,0.0085904,0.0319604,0.0224845,0.0026027,0.004623");


        //when
        ResponseEntity<ResponsePostDto> responseEntity = imageController.uploadImageAndPost(request);

        //then
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);

    }
}
