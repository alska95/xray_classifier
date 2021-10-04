package com.example.x_ray.controller;

import com.example.x_ray.dto.user.RequestLoginDto;
import com.example.x_ray.dto.user.RequestSignInDto;
import com.example.x_ray.dto.user.ResponseUserDto;
import com.example.x_ray.dto.user.UserDto;
import com.example.x_ray.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
public class UserController {

    final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public void createUser(@RequestBody RequestSignInDto requestSignInDto){
        UserDto userDto = new UserDto(
                requestSignInDto.getNickName(),
                requestSignInDto.getEmail(),
                requestSignInDto.getPassword()
        );
        userService.createUser(userDto);
    }

    @PostMapping("/user/login")
    public ResponseUserDto loginUser(@RequestBody RequestLoginDto requestLoginDto){
        UserDto userDto = new UserDto(
                requestLoginDto.getUserNickName(),
                requestLoginDto.getPassword()
        );
        UserDto loginUser = userService.validateLogin(userDto);
        if(loginUser != null){
            log.info("loginUser = [{}], loginEmail = [{}]", loginUser.getNickName() , loginUser.getEmail());
            return new ResponseUserDto(
                    loginUser.getNickName(),
                    loginUser.getEmail()
            );
            //세션 생성
        }
        return null;
    }

    @GetMapping("/user/{userNickName}")
    public ResponseUserDto findUser(@PathVariable String userNickName){
        UserDto userDto = userService.findUserByNickName(userNickName);
        return new ResponseUserDto(
                userDto.getNickName(),
                userDto.getEmail()
        );
    }

}
