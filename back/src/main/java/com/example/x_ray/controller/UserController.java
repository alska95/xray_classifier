package com.example.x_ray.controller;

import com.example.x_ray.dto.user.RequestLoginDto;
import com.example.x_ray.dto.user.RequestSignInDto;
import com.example.x_ray.dto.user.ResponseUserDto;
import com.example.x_ray.dto.user.UserDto;
import com.example.x_ray.service.user.UserService;
import org.springframework.web.bind.annotation.*;

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
    public void loginUser(@RequestBody RequestLoginDto requestLoginDto){
        UserDto userDto = new UserDto(
                requestLoginDto.getUserNickName(),
                requestLoginDto.getPassword()
        );
        UserDto loginUser = userService.validateLogin(userDto);
        if(loginUser != null){
            //세션 생성
        }
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
