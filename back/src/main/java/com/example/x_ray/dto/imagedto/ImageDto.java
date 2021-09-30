package com.example.x_ray.dto.imagedto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ImageDto {
    private String originalImageFileName;
    private String heatmapImageFileName;
    private Date createdDate;
}
