package com.example.x_ray.controller;

import com.example.x_ray.dto.user.RequestLoginDto;
import com.example.x_ray.dto.user.RequestSignInDto;

import com.example.x_ray.dto.user.ResponseUserDto;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest
@Transactional
public class UserControllerTest {
    @Autowired
    private UserController userController;



    @Test
    @Rollback(true)
    public void 회원가입후로그인() {
        String nickName = "황경하";
        String email =  "abc@naver.com";
        String password = "1234";
        RequestSignInDto requestSignInDto = new RequestSignInDto(
                nickName,
                email,
                password
        );
        userController.createUser(requestSignInDto);
        RequestLoginDto requestLoginDto = new RequestLoginDto(
                nickName,
                password
        );
        ResponseUserDto responseUserDto1 = userController.loginUser(requestLoginDto);
        ResponseUserDto responseUserDto2 = new ResponseUserDto(
                nickName,
                email
        );
        Assertions.assertThat(responseUserDto1).isEqualTo(responseUserDto2);
    }

}