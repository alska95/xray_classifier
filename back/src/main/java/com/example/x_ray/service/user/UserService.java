package com.example.x_ray.service.user;

import com.example.x_ray.dto.user.UserDto;

public interface UserService {
    public UserDto findUserByNickName(String nickName);
}
