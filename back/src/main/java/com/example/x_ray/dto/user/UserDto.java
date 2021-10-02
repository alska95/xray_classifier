package com.example.x_ray.dto.user;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {
    private String nickName;
    private String email;
    private String password;
}
