package com.example.x_ray.controller;

import com.example.x_ray.ConstVariable;
import com.example.x_ray.dto.user.RequestLoginDto;
import com.example.x_ray.dto.user.RequestSignInDto;
import com.example.x_ray.dto.user.ResponseUserDto;
import com.example.x_ray.dto.user.UserDto;
import com.example.x_ray.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.stylesheets.LinkStyle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;


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
    public ResponseUserDto loginUser(@RequestBody RequestLoginDto requestLoginDto , HttpServletRequest request){
        UserDto userDto = new UserDto(
                requestLoginDto.getUserNickName(),
                requestLoginDto.getPassword()
        );
        UserDto loginUser = userService.validateLogin(userDto);
        if(loginUser != null){
            log.info("loginUser = [{}], loginEmail = [{}]", loginUser.getNickName() , loginUser.getEmail());
            //세션 생성
            HttpSession session = request.getSession();
            session.setAttribute(ConstVariable.LOGIN_MEMBER, loginUser);
            return new ResponseUserDto(
                    loginUser.getNickName(),
                    loginUser.getEmail()
            );
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

    @GetMapping("/users")
    public List<ResponseUserDto> findAllUser(){
        List<UserDto> allUsers = userService.findAllUsers();
        return allUsers.stream().map(v -> new ResponseUserDto(
                v.getNickName(),
                v.getEmail()
        )).collect(Collectors.toList());
    }

}
