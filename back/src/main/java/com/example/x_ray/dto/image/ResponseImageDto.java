package com.example.x_ray.dto.image;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseImageDto {
    private String originalImageFileName;
    private String heatmapImageFileName;
}
