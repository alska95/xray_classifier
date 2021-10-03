package com.example.x_ray.repository.user;

import com.example.x_ray.dto.user.UserDto;
import com.example.x_ray.entity.User;

public interface UserRepository {
    public User createUser(UserDto userDto);
    public User findUser(String nickName);
}
