package com.example.x_ray.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestLoginDto {
    private String userNickName;
    private String password;
}
