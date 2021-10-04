package com.example.x_ray.service.user;

import com.example.x_ray.dto.user.UserDto;

import java.util.List;

public interface UserService {
    public UserDto createUser(UserDto userDto);
    public UserDto findUserByNickName(String nickName);
    public UserDto validateLogin(UserDto userDto);
    public List<UserDto> findAllUsers();
}
