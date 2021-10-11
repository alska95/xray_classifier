package com.example.x_ray.controller;

import com.example.x_ray.ConstVariable;
import com.example.x_ray.dto.user.RequestLoginDto;
import com.example.x_ray.dto.user.RequestSignInDto;
import com.example.x_ray.dto.user.ResponseUserDto;
import com.example.x_ray.dto.user.UserDto;
import com.example.x_ray.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
    public ResponseUserDto userDtoToResponseMapper(UserDto userDto){
        return new ResponseUserDto(
                userDto.getNickName(),
                userDto.getEmail()
        );
    }
    @GetMapping("/login-status")
    public ResponseUserDto checkLogin(HttpServletRequest request){
        HttpSession session = request.getSession();
         UserDto loginUser = (UserDto) session.getAttribute(ConstVariable.LOGIN_MEMBER);
        if(session == null || session.getAttribute(ConstVariable.LOGIN_MEMBER)== null)
            return null;
        else
            return userDtoToResponseMapper(loginUser);
    }
    @PostMapping("/user")
    public ResponseUserDto createUser(@RequestBody RequestSignInDto requestSignInDto){
        UserDto userDto = new UserDto(
                requestSignInDto.getNickName(),
                requestSignInDto.getEmail(),
                requestSignInDto.getPassword()
        );
        UserDto responseUser = userService.createUser(userDto);
        return userDtoToResponseMapper(responseUser);
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
            return userDtoToResponseMapper(loginUser);
        }
        return null;
    }

    @GetMapping("/user/{userNickName}")
    public ResponseUserDto findUser(@PathVariable String userNickName){
        UserDto userDto = userService.findUserByNickName(userNickName);
        return userDtoToResponseMapper(userDto);
    }

    @GetMapping("/users")
    public List<ResponseUserDto> findAllUser(){
        List<UserDto> allUsers = userService.findAllUsers();
        return allUsers.stream().map(v -> userDtoToResponseMapper(v)).collect(Collectors.toList());
    }

    @GetMapping("/user/logout")
    public void logoutUser(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session!=null){
            session.invalidate();
        }
    }


}
