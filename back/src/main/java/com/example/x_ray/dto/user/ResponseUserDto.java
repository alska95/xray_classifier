package com.example.x_ray.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseUserDto {
    private String nickName;
    private String email;
}
